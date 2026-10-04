
import java.util.Scanner;

public class Main {

    private static ComplexMatrix readMatrix(Scanner sc, String name) {
        System.out.println("\n--- ввод матрицы " + name + " ---");
        System.out.print("введите количество строк: ");
        int r = Integer.parseInt(sc.nextLine().trim());
        System.out.print("введите количество столбцов: ");
        int c = Integer.parseInt(sc.nextLine().trim());

        Complex[][] d = new Complex[r][c];
        System.out.println("вводите элементы строки через пробел:");
        for (int i = 0; i < r; i++) {
            while (true) {
                System.out.print("cтрока " + (i + 1) + ": ");
                String[] parts = sc.nextLine().trim().split("\\s+");
                if (parts.length != c) {
                    System.out.println("oшибка, нужно ввести ровно " + c + " элементов");
                    continue;
                }
                try {
                    for (int j = 0; j < c; j++) {
                        d[i][j] = Complex.parse(parts[j]);
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("ошибка формата числа, попробуйте еще раз.");
                }
            }
        }
        return new ComplexMatrix(d);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ComplexMatrix A = null;
        ComplexMatrix B = null;

        while (true) {
            System.out.println("\n=== выбор действия ===");
            System.out.println("1.Задать матрицу A");
            System.out.println("2.Задать матрицу B");
            System.out.println("3.Показать матрицы");
            System.out.println("4.Сложить ");
            System.out.println("5.Умножить ");
            System.out.println("6.Разделить ");
            System.out.println("7.Транспонировать");
            System.out.println("8.Вычислить определитель");
            System.out.println("0.Выход");
            System.out.print("выберите действие: ");

            String choice = sc.nextLine().trim();
            if (choice.equals("0")) break;

            try {
                switch (choice) {
                    case "1" -> A = readMatrix(sc, "A");
                    case "2" -> B = readMatrix(sc, "B");
                    case "3" -> {
                        System.out.println("\nМатрица A:\n" + (A != null ? A : "не задана"));
                        System.out.println("Матрица B:\n" + (B != null ? B : "не задана"));
                    }
                    case "4" -> {
                        if (A == null || B == null) throw new IllegalStateException("сначала введите матрицы A и B");
                        System.out.println("\nРезультат A + B:\n" + A.add(B));
                    }
                    case "5" -> {
                        if (A == null || B == null) throw new IllegalStateException("сначала введите матрицы A и B");
                        System.out.println("\nРезультат A * B:\n" + A.multiply(B));
                    }
                    case "6" -> {
                        if (A == null || B == null) throw new IllegalStateException("сначала введите матрицы A и B!");
                        System.out.println("\nРезультат A / B:\n" + A.divide(B));
                    }
                    case "7" -> {
                        System.out.print("какую матрицу транспонировать (A/B)? ");
                        String t = sc.nextLine().trim().toUpperCase();
                        ComplexMatrix target = t.equals("A") ? A : (t.equals("B") ? B : null);
                        if (target == null) throw new IllegalStateException("матрица не задана.");
                        System.out.println("\nРезультат:\n" + target.transpose());
                    }
                    case "8" -> {
                        System.out.print("для какой матрицы вычислить det (A/B)? ");
                        String t = sc.nextLine().trim().toUpperCase();
                        ComplexMatrix target = t.equals("A") ? A : (t.equals("B") ? B : null);
                        if (target == null) throw new IllegalStateException("матрица не задана.");
                        System.out.println("\nОпределитель: " + target.determinant());
                    }
                    default -> System.out.println("неверный выбор");
                }
            } catch (Exception e) {
                System.out.println("ошибка: " + e.getMessage());
            }
        }
    }
}