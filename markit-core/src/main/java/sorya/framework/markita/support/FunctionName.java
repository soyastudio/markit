package sorya.framework.markita.support;

public class FunctionName {
    private final String packageName;
    private final String functionName;

    public FunctionName(String fullName) {
        if(!fullName.contains(".")) {
            this.packageName = null;
            this.functionName = fullName;
        } else {
            int lastPoint = fullName.lastIndexOf(".");
            this.packageName = fullName.substring(0, lastPoint);
            this.functionName = fullName.substring(lastPoint + 1);
        }
    }

    public FunctionName(String packageName, String functionName) {
        this.packageName = packageName;
        this.functionName = functionName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getFunctionName() {
        return functionName;
    }
}
