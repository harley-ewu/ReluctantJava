package Class;

import Attributes.Attribute;
import Attributes.Field;
import Attributes.Method;
import com.google.gson.annotations.Expose;
import java.util.*;

public class Class {

    @Expose
    private String className;
    @Expose
    private ArrayList<Field> fields = new ArrayList<>();
    @Expose
    private ArrayList<Method> methods = new ArrayList<>();

    public Class(final String className) {
        if (className == null) {
            throw new NullPointerException("Class name is null.");
        }
        this.className = className;
    }

    /**
     * returns the current name of the class
     * @return
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
            throw new NullPointerException("Class name parameter is null or empty.");
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
        nullCheckFieldParams(name, parameters);

        Field field = new Field(name, parameters.getFirst());

        for(Field existingField : this.fields){
            if(existingField.getName().equals(name)){
                return false;
            }
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
        nullCheckMethodParams(name, parameters);

        Method method = new Method(name, parameters);

        if(this.methods.contains(method)){
            return false;
        }else{
            this.methods.add(method);
            return true;
        }
    }

    /**
     * description: deleteAttribute is a menu option method, prompting the user to enter an attribute to delete
     */
    public boolean deleteField(int input) {
        if (input < 1 || input > this.fields.size()+1) {
            return false;
        }

        this.fields.remove(input-1);
        return true;
    }

    public boolean deleteMethod(int input) {
        if (input < 1 || input > this.methods.size()+1) {
            return false;
        }

        this.methods.remove(input-1);
        return true;
    }

    /**
     * description: method that handles renaming an attribute
     * @param input - The index of the attribute in the list to be changed.
     * @param newName - The new name to for the attribute.
     * @param parameters - List of new parameters.
     *
     */
    public void renameField(int input, String newName, ArrayList<String> parameters) {
        if(input <= this.fields.size() && input >= 1  && !newName.isEmpty()) {
            for(Field existingField : this.fields){
                if(existingField.getName().equals(newName)){
                    return;
                }
            }
            this.fields.get(input - 1).setName(newName);
        }
    }

    public void renameMethod(int input, String newName, ArrayList<String> parameters) {
        if(input <= this.methods.size() && input >= 1  && !newName.isEmpty()) {
            Method method = new Method(newName, parameters);
            if(!this.methods.contains(method)){
                this.methods.get(input - 1).setName(newName);
            }
        }
    }

    /**
     * description: method that handles renaming an attribute
     * @param newParameters - List of new parameters.
     * @param index - Index of attribute to be changed.
     */
    public void renameFieldPrimitive(ArrayList<String> newParameters, int index) {
        if (index <= this.fields.size() && index >= 1) {
            this.fields.get(index - 1).setPrimitive(newParameters.getFirst());
        }
    }

    public void renameMethodParams(ArrayList<String> newParameters, int index){
        if (index <= this.methods.size() && index >= 1) {
            Method method = this.methods.get(index - 1);
            method.setParameters(newParameters);
            if(!this.methods.contains(method)){
                this.methods.get(index - 1).setParameters(newParameters);
            }
        }
    }

    /**
     * description: This method sorts the array list by either field or method.
     * @param unsortedList - The list to be sorted.
     */
    public void sortArrayList(ArrayList<Attribute> unsortedList) {
        Comparator<Attribute> arrayListComparator = new Comparator<Attribute>() {
            @Override
            public int compare(Attribute s1, Attribute s2) {
                char lastChar1 = s1.toString().charAt(s1.toString().length() - 3);
                char lastChar2 = s2.toString().charAt(s2.toString().length() - 3);
                return Character.compare(lastChar1, lastChar2);
            }
        };
        Collections.sort(unsortedList, arrayListComparator);
    }

    /*public String help() {
        String help = "How to use this menu:\n" +
                "1. Add attribute -- prompts user to enter a name for a new attribute\n" +
                "2. Delete attribute -- prompts user to enter a name of an (existing) attribute to delete\n" +
                "3. Rename attriubte -- prompts the user to enter a name of an existing attribute, then prompts user to enter a new name for that attribute\n" +
                "4. Display relationships -- displays all relationships assigned to the class\n" +
                "5. Display all contents -- displays the contents of the class including: name, attributes, and relationships\n" +
                "6. Return to Diagram Menu -- returns the user to the diagram menu holding the class\n";

        return help;
    }*/

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

        return "Class Name: " + this.getClassName() + "\n"
                +"---------------------\n"
                + "Fields: \n" + fieldsString
                + "Methods: \n" + methodsString
                + "\n";
    }

    private void nullCheckFieldParams(String name, ArrayList<String> parameters) {
        if (name.isEmpty() || parameters == null) {
            throw new IllegalArgumentException("Name cannot be empty and primitive cannot be null");
        }
    }

    private void nullCheckMethodParams(String name, ArrayList<String> parameters) {
        if (name.isEmpty() || parameters == null) {
            throw new IllegalArgumentException("Name cannot be empty and parameters cannot be null");
        }
    }

}
