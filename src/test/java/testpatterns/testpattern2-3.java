class TestPattern2_3 {

    private static final String STR = "this is a string";

    public void print(String input) {
        System.out.println(input);
    }

    public void test() {
        String str = STR;
        String str2 = STR;
        print(str);
        print(str2);
        print(STR);
        print(STR);
    }
}