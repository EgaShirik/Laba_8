import java.beans.XMLEncoder;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>(Arrays.asList(
                "Ситуация, когда электронный документ за время своего существования претерпевает ряд изменений,\n" +
                        "достаточно типична.",
                "При этом часто бывает важно иметь не только последнюю версию, но и\n" +
                        "несколько предыдущих.",
                " В простейшем случае, можно просто хранить несколько вариантов\n" +
                        "документа, соответствующим образом их нумеруя.",
                "aaa", "aaa", "bbb", "bbb", "aaa"
        ));

        int choice;
        int numOfStr;

        String subStr;

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("""

                    1)Добавить объект
                    2)Удалить объект
                    3)Просмотреть список
                    4)Найти дупликаты
                    6)Реверс
                    7)Поиск подстроки
                    8)Чтение файла
                    9)Сравнение строк
                    10)Сортировать список
                    11)Сериализовать в XML""");
            choice = sc.nextInt();

            if (choice == 1) {
                sc.nextLine();
                System.out.println("Введите строку");
                if (list.size() >= 10){
                    list.remove(0);
                }
                list.add(sc.nextLine());
            } else if (choice == 2) {
                output(list);

                System.out.println("Введите номер строки для удаления: ");
                list.remove(sc.nextInt());
            } else if (choice == 3){
                output(list);
            } else if (choice == 4) {
                findDuplicates(list);
            } else if (choice == 5) {
                collectingStatistics(list.get(0));
            } else if (choice == 6){
                System.out.println("1) Реверс строк\n2) Реверс коллекции\nВаш выбор: ");
                choice = sc.nextInt();
                if (choice == 1){
                    reverseStrings(list);
                } else if (choice == 2){
                    reverseCollection(list);
                }
                else {
                    System.out.println("Неверное введеное значение");
                }
            }
            else if (choice == 7){
                output(list);
                System.out.println("Выбор строки: ");
                numOfStr = sc.nextInt();
                sc.nextLine();
                System.out.println("Введите подстроку: ");
                subStr = sc.nextLine();
                substringSearch(subStr, list.get(numOfStr));
            } else if (choice == 8){
                fileReader();
            } else if (choice == 9){
                output(list);
                compareInnerObjects(list, sc.nextInt(), sc.nextInt());
            } else if (choice == 10){
                sortList(list);
            } else if (choice == 11){
                serialise(list);
            } else {
                break;
            }
        }
    }

    public static void output(ArrayList<String> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1)  + "- " + list.get(i));
        }
    }

    public static void reverseStrings(ArrayList<String> list){
        ArrayList<String> reverseList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            reverseList.add(new StringBuilder(list.get(i)).reverse().toString());
        }

        output(reverseList);
    }

    public static void reverseCollection(ArrayList<String> list){
        ArrayList<String> reverseList = list;
        Collections.reverse(reverseList);

        output(reverseList);
    }

    public static void findDuplicates(ArrayList<String> list) {
        Set<String> unique = new HashSet<>(list);
        for (String key : unique) {
            if (Collections.frequency(list, key) != 1){
                System.out.println(key + ": " + Collections.frequency(list, key));
            }
        }
    }

    public static void collectingStatistics(String str){
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            Integer val = map.get(c);
            if (val != null) {
                map.put(c, val + 1);
            }
            else {
                map.put(c, 1);
            }
        }
        Iterator<Map.Entry<Character, Integer>> iterator = map.entrySet().iterator();

        while (iterator.hasNext())
        {
            Map.Entry<Character, Integer> pair = iterator.next();
            Character key = pair.getKey();
            Integer value = pair.getValue();
            System.out.println(key + ":" + value);
        }
    }

    public static void substringSearch(String subStr, String str){

        Pattern pattern = Pattern.compile(".*" + subStr + ".*");

        Matcher matcher = pattern.matcher(str);
        System.out.println("Ответ: " + matcher.find());
    }

    public static void fileReader() throws IOException {
        ArrayList<String> result = new ArrayList<>();
        File myfile = new File("D:\\ПИСМ\\Laba_8\\src\\List");
        FileReader fr = new FileReader(myfile);
        BufferedReader reader = new BufferedReader(fr);
        String line;
        while ((line = reader.readLine()) != null) {
            result.add(line);
        }
        reader.close();
        output(result);
    }

    public static void compareInnerObjects(ArrayList<String> list, int firstIndex, int secondIndex){
        if (list.get(firstIndex).equals(list.get(secondIndex))){
            System.out.println("Строки равны");
        } else {
            System.out.println("Строки не равны");
        }
    }

    public static void sortList(ArrayList<String> list){
        String[] arr = list.toArray(new String[list.size()]);
        for(int i = arr.length - 1; i > 0 ; i--){
            for(int j = 0 ; j < i ; j++){
                if( arr[j].length() > arr[j+1].length() ){
                    String tmp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = tmp;
                }
            }
        }

        for(int i = 0; i < arr.length; i++){
            System.out.println(arr[i]);
        }
    }

    public static void serialise(ArrayList<String> list) throws FileNotFoundException {
        XMLEncoder e = new XMLEncoder(new BufferedOutputStream(
                new FileOutputStream("Test.xml")));
        e.writeObject(list);
        e.close();
    }
}
