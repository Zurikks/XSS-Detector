package sample;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.w3c.dom.NodeList;

import java.util.List;


public class Test {

    public String formTest() throws Exception {

        try (final WebClient client = new WebClient()) {

            final HtmlPage page = client.getPage("http://localhost/xss/example/");
            System.out.println(page.getTitleText());


            List<HtmlForm> formList = page.getForms();
            int i;
            NodeList inputs = page.getElementsByTagName("form");
            final HtmlSubmitInput button = (HtmlSubmitInput) page.getElementById("submitButton");
            HtmlPage page2 = page;


            for(i = 0; i < inputs.getLength(); i++) {

                System.out.println(formList.get(i).toString()+ "\n");
                String currentForm  = formList.get(i).toString().toLowerCase();


                if(formList != null) {


                    if(page.getElementById("login") != null) {

                        String loginForm = page.getFirstByXPath("form//[@name='login']");
                        if(currentForm.equals(loginForm)) {

                            System.out.println(formList.get(i).getNameAttribute());
                            HtmlTextArea userNameInput = page.getFirstByXPath("//textarea[@name='username'");
                            userNameInput.type("root");
                            HtmlTextArea passwordText = page.getFirstByXPath("//textarea[@name='password'");
                            passwordText.type("root");
                            page2 = button.click();
                            return page2.asXml();
                        }
                    }

                    if(page.getElementById("comment") != null) {

                        String commentForm = page.getFirstByXPath("//form[@id='comment']").toString().toLowerCase();

                        if (currentForm.equals(commentForm)) {

                            HtmlTextArea commentInput = page.getFirstByXPath("//textarea[@name='comment']");
                            commentInput.type("<script>window.alert(\"XSS\")</script>This is the XSS comment");
                            page2 = button.click();
                        }
                    }
                    /*fix checking for scripting errors by searching for <script>
                    tags in comments*/
                }
            }
            return page2.asXml();
        }
    }
}