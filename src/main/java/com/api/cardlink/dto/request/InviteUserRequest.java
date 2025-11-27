package com.api.cardlink.dto.request;





import com.api.cardlink.enumeration.UserRole;
import lombok.Data;

@Data
public class InviteUserRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private UserRole role = UserRole.AGENT;
}