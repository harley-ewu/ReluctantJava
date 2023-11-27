package Class;

import Attributes.Field;
import Attributes.Method;
import com.google.gson.annotations.Expose;
import java.util.*;

public class Class implements Cloneable{

    @Expose
    private String className;
    @Expose
    private ArrayList<Field> fields = new ArrayList<>();
    @Expose
    private ArrayList<Method> methods = new ArrayList<>();

    public Class(final String className) throws IllegalArgumentException {
        if (className == null) {
            throw new IllegalArgumentException("Class name is null.");
        }
        this.className = className;
    }

    /**
     * returns the current name of the class
     * @return - returns a string with the name of the class
     */
    //getters
    public String getClassName() {
        return this.className;
    }

    /**
     * description: used to set a name for the class or rename a class
     * @param newClassName - the name of the new class a user wants to rename a class to
     */
    //setters
    public void setClassName(final String newClassName) {
        if (newClassName == null || newClassName.isEmpty()) {
            throw new IllegalArgumentException("Class name parameter is null or empty.");
        }

        this.className = newClassName;
    }

    /**
     * Description: Gets the list of fields currently in the class
     * @return - returns the list of fields in the class
     */
    public ArrayList<Field> getFields() {
        return fields;
    }

    /**
     * Description: Sets the list of fields in the class to a new list
     * @param fields - the new list of fields
     */
    public void setFields(ArrayList<Field> fields) {
        this.fields = fields;
    }

    /**
     * Description: Gets the list of methods currently in the class
     * @return - returns the list of the methods
     */
    public ArrayList<Method> getMethods() {
        return methods;
    }

    /**
     * Description: Sets the list of methods in the class to a new list.
     * @param methods - the new list of methods
     */
    public void setMethods(ArrayList<Method> methods) {
        this.methods = methods;
    }

    /**
     * Description: Creates a new field and adds it to the list of fields if
     * a field with the same name does not exist.
     * @param name - the name of the new field
     * @param parameters - the primitive of the new field
     * @return - returns true if successfully adds new field,
     * returns false if a field of the same name exists.
     */
    public boolean createField(String name, ArrayList<String> parameters) {
        if (name == null || name.isEmpty() || parameters == null || parameters.isEmpty()) {
            return false;
        }

        Field field = new Field(name, parameters.get(0));

        for(Field existingField : this.fields)
            if (existingField.getName().equals(name)) {
                return false;
            }

        this.fields.add(field);
        return true;
    }

    /**
     * Description: Creates a new method and adds it to the list if
     * a methods with the same name and params does not exist.
     * @param name - The name of the new method
     * @param parameters - The params of the new method
     * @return - Returns true if successfully adds the method,
     * returns false if a method of the same name and parameters exists.
     */
    public boolean createMethod(String name, ArrayList<String> parameters) {
        if (name == null || name.isEmpty() || parameters == null) {
            return false;
        }

        Method method = new Method(name, parameters);
        for(Method existingMethod : this.methods){
            if(existingMethod.equals(method)){
                return false;
            }
        }
        this.methods.add(method);
        return true;
    }

    /**
     * Description: Deletes a field at the specified index from the list of fields
     * @param input - the index of the field
     * @return - returns true if successful delete, returns false if index out of bounds.
     */
    public boolean deleteField(int input) {
        if (input < 1 || input > this.fields.size()) {
            return false;
        }

        this.fields.remove(input-1);
        return true;
    }

    /**
     * Description: Deletes a method at a specified index from the list of methods
     * @param input - the index of the method
     * @return - returns true if successfully deletes, returns false if index out of bounds
     */
    public boolean deleteMethod(int input) {
        if (input < 1 || input > this.methods.size()) {
            return false;
        }

        this.methods.remove(input-1);
        return true;
    }

    /**
     * Description: Renames an existing field in the list of fields
     * @param input - the index of the field to be renamed
     * @param newName - the new name for the field
     * @return - returns true if renaming the field is successful, returns false if the index
     * of the field is out of bounds or if the new name exists as the name of a different
     * field in the list of fields
     */
    public boolean renameField(int input, String newName) {
        if(input > this.fields.size() || input < 1  || newName == null || newName.isEmpty()) {
            return false;
        }
        for(Field existingField : this.fields){
            if(existingField.getName().equals(newName)){
                return false;
            }
        }
        this.fields.get(input - 1).setName(newName);
        return true;
    }

    /**
     * Description: Renames an existing method in the list of methods.
     * @param input - the index of the method to be renamed
     * @param newName - the new name for the existing method
     * @param parameters - the params of the existing method.
     * @return - returns true if successfully renames method, returns false if the index is out of bounds
     * or if the resulting method already exists in the list of methods
     */
    public boolean renameMethod(int input, String newName, ArrayList<String> parameters) {
        if(input > this.methods.size() || input < 1  || newName == null || newName.isEmpty() || parameters == null) {
            return false;
        }
        Method method = new Method(newName, parameters);
        if(!this.methods.contains(method)){
            this.methods.get(input - 1).setName(newName);
            return true;
        }
        return false;
    }

    /**
     * Description: Renames the primitive of a field in the list of fields
     * @param newParameters - the name of the new primitive for the existing field
     * @param index - the index of the field to have its parameter name changed
     * @return - Returns true if successfully renames the fields primitive,
     * returns false if the index for the field is out of bounds.
     */
    public boolean renameFieldPrimitive(ArrayList<String> newParameters, int index) {
        if (index > this.fields.size() || index < 1 || newParameters == null || newParameters.isEmpty()) {
            return false;
        }
        this.fields.get(index - 1).setPrimitive(newParameters.get(0));
        return true;
    }

    /**
     * Description: Renames the parameters of a method in the list of methods
     * @param newParameters - the list of new parameters
     * @param index - the index of the method to have its params changed
     * @return - returns true if the params for the method are successfully changed,
     * returns false if the index for the method is out of bounds or if a method of
     * the same name and params already exists in the list of methods.
     */
    public boolean renameMethodParams(ArrayList<String> newParameters, int index){
        if (index > this.methods.size() || index < 1 || newParameters == null) {
            return false;
        }
        Method method = new Method(this.methods.get(index - 1).getName(), newParameters);
        if(!this.methods.contains(method)){
            this.methods.get(index - 1).setParameters(newParameters);
            return true;
        }
        return false;
    }

    /**
     * description: returns a string of all attributes currently within the class
     * @return
     */
    public String displayAttributes() {
        StringBuilder display = new StringBuilder();

        display.append("Available Fields and Methods: \n");

        display.append("Fields: \n");

        int i = 0;

        for (Field field : this.fields) {
            display.append(i + 1)
                    .append(". ")
                    .append(field.toString().replaceAll("[\\[\\]]", ""))
                    .append("\n");
            i++;
        }

        display.append("Methods: \n");

        i = 0;

        for (Method method : this.methods) {
            display.append(i + 1)
                    .append(". ")
                    .append(method.toString().replaceAll("[\\[\\]]", ""))
                    .append("\n");
            i++;
        }

        return display.toString();
    }

    /**
     * description: toString will display all contents of the class object including: name, attributes, and relationships
     * @return
     */
    @Override
    public String toString() {
        StringBuilder fieldsString = new StringBuilder();
        StringBuilder methodsString = new StringBuilder();

        for (Field field : this.fields) {
            fieldsString.append(field.toString().replaceAll("[\\[\\]]", "")).append("\n");
        }

        for (Method method : this.methods) {
            methodsString.append(method.toString().replaceAll("[\\[\\]]", "")).append("\n");
        }

        return "\n--------------------------\n" + "Class Name: " + this.getClassName() + "\n"
                +"--------------------------\n"
                + "Fields: \n" + fieldsString
                + "Methods: \n" + methodsString
                + "\n"
                + "--------------------------\n";
    }

    @Override
    public Class clone() {
        try {
            Class clonedClass = (Class) super.clone();
            clonedClass.fields = new ArrayList<>(this.fields.size());
            for (Field field : this.fields) {
                clonedClass.fields.add(field.clone());
            }
            clonedClass.methods = new ArrayList<>(this.methods.size());
            for (Method method : this.methods) {
                clonedClass.methods.add(method.clone());
            }
            return clonedClass;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

}
