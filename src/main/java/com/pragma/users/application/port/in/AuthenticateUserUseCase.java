
package com.pragma.users.application.port.in;

public interface AuthenticateUserUseCase {

    String authenticate(String email, String password);
}
