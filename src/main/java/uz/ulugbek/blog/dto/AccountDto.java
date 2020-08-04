package uz.ulugbek.blog.dto;

import lombok.Data;

@Data
public class AccountDto {

    private Long id;

    private String username;

    private String password;

    private String note;

    private int active;

    private String fio;

}
