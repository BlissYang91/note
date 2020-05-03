public class EditText {
    public static void setEditTextLengthLimit( EditText editText, int length) {
        editText.setFilters( new InputFilter[]{new InputFilter.LengthFilter(length)});
    }
}