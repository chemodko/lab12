public class Vedomost {

    private static final int COLUMN = 6;
    private String[][] mtx;
    private int row;

    public Vedomost(int row) {
        this.row = row;
        mtx = new String[row][COLUMN];
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return COLUMN;
    }

    public void setElement(int row, int column, String value) {
        mtx[row][column] = value;
    }

    public String getElement(int row, int column) {
        return mtx[row][column];
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("");
        for (int i = 0; i < getRow(); i++) {
            for (int j = 0; j < getColumn(); j++) {
                str.append(this.getElement(i, j) + " ");
            }
            str.append("\n");
        }
        return str.toString();
    }

    @Override
    public boolean equals(Object ob) {
        if (this == ob) return true;
        if (ob == null) return false;
        if (!(ob instanceof Vedomost)) return false;
        Vedomost mt = (Vedomost) ob;
        if (this.getRow() != mt.getRow() || this.getColumn() != mt.getColumn()) return false;
        for (int i = 0; i < getRow(); i++) {
            for (int j = 0; j < getColumn(); j++) {
                if (this.getElement(i, j) != mt.getElement(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }
}