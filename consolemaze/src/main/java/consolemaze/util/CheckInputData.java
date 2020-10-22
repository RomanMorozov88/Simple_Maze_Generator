package consolemaze.util;

public class CheckInputData {

    /**
     * Проверка, что входящую строку можно преобразовать в Integer.
     *
     * @param s
     * @return
     * @throws NumberFormatException
     */
    public static Integer getDigit(String s) throws NumberFormatException {
        Integer result = null;
        try {
            result = Integer.parseInt(s);
            return result;
        } catch (NumberFormatException e) {
            return result;
        }
    }

}