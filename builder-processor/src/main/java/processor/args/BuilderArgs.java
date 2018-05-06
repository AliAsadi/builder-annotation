package processor.args;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;

import java.util.ArrayList;

/**
 * Created by Ali Esa Assadi on 03/05/2018.
 */
public class BuilderArgs {

    private ArrayList<FieldSpec> fieldList = new ArrayList<>();
    private ArrayList<MethodSpec> methodList = new ArrayList<>();

    public BuilderArgs(ArrayList<FieldSpec> fieldList, ArrayList<MethodSpec> methodList) {
        this.fieldList = fieldList;
        this.methodList = methodList;
    }

    public ArrayList<FieldSpec> getFieldList() {
        return fieldList;
    }

    public ArrayList<MethodSpec> getMethodList() {
        return methodList;
    }

}
