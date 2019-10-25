class TestPattern2_2 {

    private String str1 = "this is a string";
    private String str12 = "this is a string";

    public void print(String input) {
        System.out.println(input);
    }

    public void test() {
        String str = "this is a string";
        String str2 = str1;
        print("this is a string");
        print("this is a string");
        print("this is a string");
        print("this is a string");
    }
}