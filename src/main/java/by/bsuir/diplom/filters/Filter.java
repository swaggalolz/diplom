package by.bsuir.diplom.filters;

public interface Filter {
    int[] transform(int width, int height, int[] pixels);
}
