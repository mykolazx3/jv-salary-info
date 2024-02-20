package core.basesyntax;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SalaryInfo {

    private LocalDate localDateFrom;
    private LocalDate localDateTo;
    private LocalDate [] consistencyLocalData;
    private String [] consistencyEmployee;
    private int [] consistencyWorkingHourDuringTheParticularDay;
    private int [] consistencyIncomePerHour;
    private int [] sum;
    private StringBuilder stringBuilder;

    public String getSalaryInfo(String[] names, String[] data, String dateFrom, String dateTo) {

        localDateFrom = getDate(dateFrom);
        localDateTo = getDate(dateTo);
        consistencyLocalData = new LocalDate[data.length];
        consistencyEmployee = new String[data.length];
        consistencyWorkingHourDuringTheParticularDay = new int [data.length];
        consistencyIncomePerHour = new int[data.length];
        sum = new int[names.length];
        stringBuilder = new StringBuilder()
                .append("Report for period ").append(dateFrom).append(" - ").append(dateTo);

        splitDataToArrays(data);

        calculateSalaryForPeriod(names);

        return stringBuilder.toString();

    }

    private LocalDate getDate(String input) {
        final DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(input, formatter);
    }

    private void splitDataToArrays(String[] data) {
        for (int i = 0; i < data.length; i++) {
            String [] dataSplit = data[i].split(" ");

            consistencyLocalData[i] = getDate(dataSplit[0]);
            consistencyEmployee[i] = dataSplit[1];
            consistencyWorkingHourDuringTheParticularDay[i] = Integer.parseInt(dataSplit[2]);
            consistencyIncomePerHour[i] = Integer.parseInt(dataSplit[3]);
        }
    }

    private void calculateSalaryForPeriod(String[] names) {
        for (int i = 0; i < names.length; i++) {

            for (int j = 0; j < consistencyEmployee.length; j++) {
                if (consistencyEmployee[j].equals(names[i])) {

                    if (consistencyLocalData[j].isEqual(localDateFrom)
                            || consistencyLocalData[j].isEqual(localDateTo)
                            || consistencyLocalData[j].isAfter(localDateFrom)
                            && consistencyLocalData[j].isBefore(localDateTo)) {

                        sum[i] += (consistencyWorkingHourDuringTheParticularDay[j]
                                * consistencyIncomePerHour[j]);
                    }
                }
            }

            stringBuilder.append(System.lineSeparator()).append(names[i]).append(" - ")
                    .append(sum[i]);
        }
    }
}
