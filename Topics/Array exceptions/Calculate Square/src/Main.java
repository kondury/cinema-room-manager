class FixingExceptions {

    public static void calculateSquare(int[] array, int index) {
        if (array == null || index < 0 || index > array.length - 1) {
            System.out.println("Exception!");
        } else {
            int n = array[index];
            System.out.println(n * n);
        }
    }
}