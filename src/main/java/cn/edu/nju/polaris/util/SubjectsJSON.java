package cn.edu.nju.polaris.util;

import cn.edu.nju.polaris.entity.Subjects;
import cn.edu.nju.polaris.repository.SubjectsRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.json.Json;

import java.util.Arrays;
import java.util.List;

@Component
public class SubjectsJSON {

    private final SubjectsRepository subjectsRepository;


    public SubjectsJSON(SubjectsRepository subjectsRepository) {
        this.subjectsRepository = subjectsRepository;
    }

    void SubjectJSON() throws JSONException {

        JSONObject object1 = new JSONObject();
        object1.put("value", "1");
        object1.put("label", "资产");
        object1.put("children", new JSONArray());

        JSONObject object2 = new JSONObject();
        object2.put("value", "2");
        object2.put("label", "负债");
        object2.put("children", new JSONArray());

        JSONObject object3 = new JSONObject();
        object3.put("value", "3");
        object3.put("label", "净资产");
        object3.put("children", new JSONArray());

        JSONObject object4 = new JSONObject();
        object4.put("value", "4");
        object4.put("label", "收入");
        object4.put("children", new JSONArray());

        JSONObject object5 = new JSONObject();
        object5.put("value", "5");
        object5.put("label", "费用");
        object5.put("children", new JSONArray());

        JSONObject object6 = new JSONObject();
        object6.put("value", "6");
        object6.put("label", "其他");
        object6.put("children", new JSONArray());

        JSONObject[] array = new JSONObject[]{object1, object2, object3, object4, object5, object6};

        List<Subjects> list = subjectsRepository.findAll();
        for (Subjects s : list) {
            JSONObject object = new JSONObject();
            object.put("value", s.getSubjectsId());
            object.put("label", s.getSubjectsId() + " " + s.getSubjectsName());
            switch (s.getType()) {
                case "资产":
                    array[0].getJSONArray("children").put(object);
                    break;
                case "负债":
                    array[1].getJSONArray("children").put(object);
                    break;
                case "净资产":
                    array[2].getJSONArray("children").put(object);
                    break;
                case "收入":
                    array[3].getJSONArray("children").put(object);
                    break;
                case "费用":
                    array[4].getJSONArray("children").put(object);
                    break;
                case "其他":
                    array[5].getJSONArray("children").put(object);
                    break;
            }
        }
        System.out.println(Arrays.toString(array));

    }


}
