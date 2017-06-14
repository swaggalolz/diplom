package by.bsuir.diplom.perceptron.struct;


import java.io.Serializable;

public class PreInput implements Serializable {

    private double value;
    private double[] weights;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double[] getWeights() {
        return weights;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }
}
