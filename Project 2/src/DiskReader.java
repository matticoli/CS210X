public class DiskReader implements DataProvider<Integer, String> {

    DiskReader() {
    }


    @Override
    public String get(Integer key) {
        return key + "";
    }
}
