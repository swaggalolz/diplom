package by.bsuir.diplom.text;


public class Letter {

    private static char $_ = '_';
    private static char $  = '<';

    public Letter(String maxProbability, String minProbability) {
        this.maxProbability = maxProbability.charAt(0) == $_ ? $ : maxProbability.charAt(0) ;
        this.minProbability = minProbability.charAt(0) == $_ ? $ : minProbability.charAt(0) ;
    }

    public char maxProbability;
    public char minProbability;

}
