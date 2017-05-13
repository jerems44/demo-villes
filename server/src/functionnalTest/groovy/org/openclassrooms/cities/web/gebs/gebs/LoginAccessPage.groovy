package org.openclassrooms.cities.web.gebs.gebs

import org.openclassrooms.cities.web.gebs.gebs.page.HomePage
import org.openclassrooms.cities.web.gebs.gebs.page.LoginPage

/**
 * Created by jguidoux on 12/05/2017.
 */
class LoginAccessPage extends BaseGebsSpec {

    def "login with valid user in the login page"() {

        when:
        LoginPage loginPage = to(LoginPage)
        then:
        at LoginPage

        when:
        loginPage.username = "user"
        loginPage.password = "password"
        loginPage.submitButton.click()

        then:
        at HomePage


    }


}