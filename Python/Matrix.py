import random


class Matrix:
    def __init__(self, rows, cols):
        if type(rows) == "list":
            self.cols = len(rows[0])
            self.rows = rows.length
            self.data = rows
        else:
            self.cols = cols
            self.rows = rows
            self.data = []
            for i in range(self.rows):
                self.data.append([])
                for j in range(self.cols):
                    self.data[i].append(0)

    def print(self):
        print(self.data)

    @staticmethod
    def from_array(arr):
        m = Matrix(len(arr), 1)
        for i in range(len(arr)):
            m.data[i][0] = arr[i]
        return m

    @staticmethod
    def subtract(a, b):
        m = Matrix(a.rows, a.cols)
        for i in range(a.rows):
            for j in range(a.cols):
                m.data[i][j] = a.data[i][j] - b.data[i][j]
        return m

    @staticmethod
    def multiply(a, b):
        # dot product
        if a.cols == b.rows:
            m = Matrix(a.rows, b.cols)
            for i in range(a.rows):
                for j in range(b.cols):
                    total = 0
                    for k in range(b.rows):
                        total += a.data[i][k] * b.data[k][j]
                    m.data[i][j] = total
            return m
        else:
            raise ValueError("Columns should match rows of the matrix.")

    @staticmethod
    def transpose(m):
        # use cols in place of rows
        result = Matrix(m.cols, m.rows)
        for i in range(m.rows):
            for j in range(m.cols):
                result.data[j][i] = m.data[i][j]
        return result

    def randomize(self):
        for i in range(self.rows):
            for j in range(self.cols):
                self.data[i][j] = random.random() * 2 - 1

    def multiply_element_wise(self, m):
        if isinstance(m, Matrix):
            for i in range(self.rows):
                for j in range(self.cols):
                    self.data[i][j] *= m.data[i][j]
        else:
            for i in range(self.rows):
                for j in range(self.cols):
                    self.data[i][j] *= m

    def add(self, m):
        if isinstance(m, Matrix):
            for i in range(self.rows):
                for j in range(self.cols):
                    self.data[i][j] += m.data[i][j]
        else:
            for i in range(self.rows):
                for j in range(self.cols):
                    self.data[i][j] += m

    def map(self, df):
        m = Matrix(self.rows, self.cols)
        for i in range(self.rows):
            for j in range(self.cols):
                m.data[i][j] = df(self.data[i][j])
        return m

    def apply(self, df):
        for i in range(self.rows):
            for j in range(self.cols):
                self.data[i][j] = df(self.data[i][j])

    def to_array(self):
        array = []
        for i in range(self.rows):
            for j in range(self.cols):
                array.append(self.data[i][j])
        return array