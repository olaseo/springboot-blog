package me.olaseo.springbootdeveloper.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller // 컨트롤러라는 것을 명시적으로 표시
public class ExampleController {

    @GetMapping("/thymeleaf/example")
    public String thymeleafExample(Model model) { // Model 클래스를 파라미터로 받는 함수
        // 모델 객체는 뷰, HTML 쪽으로 값을 넘겨 주는 객체
        Person examplePerson = new Person(); // 객체 새로 생성
        examplePerson.setId(1L); // 객체 내에 값 주입
        examplePerson.setName("홍길동");
        examplePerson.setAge(11);
        examplePerson.setHobbies(List.of("운동", "독서"));

        // addAttribute() 메서드로 모델에 값을 저장한다.
        // 키를 지정하고, 키 안에 정보를 저장한다.
        model.addAttribute("person", examplePerson); // Person 객체 저장
        model.addAttribute("today", LocalDate.now());

        return "example"; // example.html이라는 뷰 조회
    }

    @Getter
    @Setter
    class Person {
        private Long id;
        private String name;
        private int age;
        private List<String> hobbies;
    }
}
