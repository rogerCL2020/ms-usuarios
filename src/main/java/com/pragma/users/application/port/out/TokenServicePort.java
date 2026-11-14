
package com.pragma.users.application.port.out;

public interface TokenServicePort {

    String generateToken(Long userId,String email, String role);
}
