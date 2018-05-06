package processor;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

/**
 * Created by Ali Esa Assadi on 03/05/2018.
 */
class ValidationUtils {

    private ValidationUtils() {
        throw new UnsupportedOperationException();
    }

    static boolean isKindOfClass(Element annotatedElement) {
        return annotatedElement.getKind() == ElementKind.CLASS;
    }

}
