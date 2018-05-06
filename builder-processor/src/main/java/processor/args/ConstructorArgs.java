package processor.args;

/**
 * Created by Ali Esa Assadi on 03/05/2018.
 */
public class ConstructorArgs {
    private StringBuilder fieldsName = new StringBuilder();
    private boolean isConstructorHaveAllParams = false;

    public ConstructorArgs(StringBuilder fieldsName, boolean isConstructorHaveAllParams) {
        this.fieldsName = fieldsName;
        this.isConstructorHaveAllParams = isConstructorHaveAllParams;
    }

    public StringBuilder getFieldsName() {
        return fieldsName;
    }

    public boolean isConstructorHaveAllParams() {
        return isConstructorHaveAllParams;
    }
}
