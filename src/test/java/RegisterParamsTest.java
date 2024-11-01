import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pojo.RegisterRequest;

@RunWith(Parameterized.class)
@Feature(value = "Не заполнено одно из обязательных полей")
public class RegisterParamsTest extends AbstractApiTest {

    private String name;
    private String password;
    private String email;

    public RegisterParamsTest(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    @Parameterized.Parameters
    public static Object[][] getUserData() {
        return new Object[][] {
                { "", "12345", "Eugenia@mail.ru" },
                { null, "12345", "Eugenia@mail.ru" },
                { "sprhero03", "", "Eugenia@mail.ru" },
                { "sprhero03", null, "Eugenia@mail.ru" },
                { "sprhero03", "12345", "" },
                { "sprhero03", "12345", null },
                { null, null, "" },
                { "", null, "" },
                { null, "", null },
                { "", "", null },

        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = URL;
    }

    @Test
    @DisplayName("Не заполнено одно из обязательных полей - ошибка 403")
    public void createNoRequiredFieldFail() {

        RegisterRequest registerRequest = new RegisterRequest(name, password, email);

        Response response = ApiHelper.postRegister(registerRequest);
        ApiHelper.checkResponseCode(response,403);
        ApiHelper.checkResponseMessage(response, "Email, password and name are required fields");

    }

}
