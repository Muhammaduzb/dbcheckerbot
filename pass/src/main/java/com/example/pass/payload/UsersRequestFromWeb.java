package com.example.pass.payload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersRequestFromWeb {
    @Size(min = 11, max = 11, message = "PhoneNumber must be 11 characters.")
    private String phoneNumber;
    @Size(min = 2, message = "Message must be min 2 characters.")
    private String message;
}