import java.util.Locale;

public class Complex {
    double re;
    double im;

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }
    public Complex add(Complex o) {
        return new Complex(this.re + o.re, this.im + o.im);
    }
    public Complex sub(Complex o) {
        return new Complex(this.re - o.re, this.im - o.im);
    }
    public Complex mul(Complex o) {
        return new Complex(this.re * o.re - this.im * o.im, this.re * o.im + this.im * o.re);
    }
    public Complex div(Complex o) {
        double denom = o.re * o.re + o.im * o.im;
        if (denom == 0) {
            throw new ArithmeticException("деление на нулевое комплексное число");
        }
        return new Complex(
                (this.re * o.re + this.im * o.im) / denom,
                (this.im * o.re - this.re * o.im) / denom
        );
    }
    @Override
    public String toString() {
        if (im == 0) return String.format(Locale.US, "%.2f", re);
        if (re == 0) return String.format(Locale.US, "%.2fi", im);
        return String.format(Locale.US, "%.2f%s%.2fi", re, (im > 0 ? "+" : ""), im);
    }
    public static Complex parse(String s) {
        s = s.trim().replace(" ", "").replace("j", "i");
        if (s.isEmpty()) return new Complex(0, 0);
        if (!s.contains("i")) return new Complex(Double.parseDouble(s), 0);

        int sign = Math.max(s.lastIndexOf('+'), s.lastIndexOf('-'));
        if (sign <= 0) {
            String imStr = s.replace("i", "");
            if (imStr.isEmpty() || imStr.equals("+")) return new Complex(0, 1);
            if (imStr.equals("-")) return new Complex(0, -1);
            return new Complex(0, Double.parseDouble(imStr));
        }

        double re = Double.parseDouble(s.substring(0, sign));
        String imStr = s.substring(sign, s.length() - 1);
        double im = imStr.equals("+") ? 1 : (imStr.equals("-") ? -1 : Double.parseDouble(imStr));
        return new Complex(re, im);
    }
}
