public class ComplexMatrix {
    int rows;
    int cols;
    Complex[][] data;
    public ComplexMatrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new Complex[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = new Complex(0, 0);
            }
        }
    }
    public ComplexMatrix(Complex[][] data) {
        this.rows = data.length;
        this.cols = data[0].length;
        this.data = new Complex[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(data[i], 0, this.data[i], 0, cols);
        }
    }
    public ComplexMatrix add(ComplexMatrix o) {
        if (this.rows != o.rows || this.cols != o.cols) {
            throw new IllegalArgumentException("размеры матриц должны совпадать");
        }
        ComplexMatrix res = new ComplexMatrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                res.data[i][j] = this.data[i][j].add(o.data[i][j]);
            }
        }
        return res;
    }
    public ComplexMatrix multiply(ComplexMatrix o) {
        if (this.cols != o.rows) {
            throw new IllegalArgumentException("число столбцов первой матрицы должно быть равно числу строк второй");
        }
        ComplexMatrix res = new ComplexMatrix(this.rows, o.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < o.cols; j++) {
                Complex sum = new Complex(0, 0);
                for (int k = 0; k < this.cols; k++) {
                    sum = sum.add(this.data[i][k].mul(o.data[k][j]));
                }
                res.data[i][j] = sum;
            }
        }
        return res;
    }
    public ComplexMatrix transpose() {
        ComplexMatrix res = new ComplexMatrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                res.data[j][i] = this.data[i][j];
            }
        }
        return res;
    }
    private ComplexMatrix minor(int row, int col) {
        ComplexMatrix m = new ComplexMatrix(rows - 1, cols - 1);
        int r = 0;
        for (int i = 0; i < rows; i++) {
            if (i == row) continue;
            int c = 0;
            for (int j = 0; j < cols; j++) {
                if (j == col) continue;
                m.data[r][c++] = data[i][j];
            }
            r++;
        }
        return m;
    }
    public Complex determinant() {
        if (rows != cols) {
            throw new IllegalStateException("определитель существует только для квадратных матриц");
        }
        if (rows == 1) return data[0][0];
        if (rows == 2) {
            return data[0][0].mul(data[1][1]).sub(data[0][1].mul(data[1][0]));
        }

        Complex det = new Complex(0, 0);
        for (int j = 0; j < cols; j++) {
            Complex sign = (j % 2 == 0) ? new Complex(1, 0) : new Complex(-1, 0);
            det = det.add(sign.mul(data[0][j]).mul(minor(0, j).determinant()));
        }
        return det;
    }
    public ComplexMatrix inverse() {
        Complex d = determinant();
        if (d.re == 0 && d.im == 0) {
            throw new ArithmeticException("определитель равен 0, обратной матрицы не существует");
        }

        ComplexMatrix adj = new ComplexMatrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Complex sign = ((i + j) % 2 == 0) ? new Complex(1, 0) : new Complex(-1, 0);
                adj.data[j][i] = sign.mul(minor(i, j).determinant()); // Сразу транспонируем
            }
        }

        ComplexMatrix inv = new ComplexMatrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                inv.data[i][j] = adj.data[i][j].div(d);
            }
        }
        return inv;
    }
    public ComplexMatrix divide(ComplexMatrix o) {
        return this.multiply(o.inverse());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            sb.append("[ ");
            for (int j = 0; j < cols; j++) {
                sb.append(String.format("%8s ", data[i][j]));
            }
            sb.append("]\n");
        }
        return sb.toString();
    }
}