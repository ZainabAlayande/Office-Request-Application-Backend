package africa.semicolon.remApp.services.notification.template;

import java.math.BigDecimal;

public class MakeRequest {

    public static String makeRequestTemplate(String name) {
        return "<div id=\"template\" style=\"width:600px;margin: auto;background:white;\">\n" +
                "  <table role=\"presentation\" border=\"0\" align=\"right\" cellspacing=\"0\">\n" +
                "    <tr>\n" +
                "      <td>\n" +
                "        <a href=\"#\" style=\"font-size: 9px; text-transform:uppercase; letter-spacing: 1px; color: #99ACC2;  font-family:Arial;\">View in Browser</a>\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </table>\n" +
                "   \n" +
                "  <!-- Header --> \n" +
                "  <table role=\"presentation\" border=\"0\" width=\"100%\" cellspacing=\"0\">\n" +
                "  <tr>\n" +
                "    <td bgcolor=\"#00A4BD\" align=\"center\" style=\"color: white;\">\n" +
                "     <h1 style=\"font-size: 52px; margin:0 0 20px 0; font-family:Arial;\">Spedire</h1>\n" +
                "    </tr>\n" +
                "      </td>\n" +
                "  </table>" +
                " <!-- Body 1 --> \n" +
                "  <table role=\"presentation\" border=\"0\" width=\"100%\" cellspacing=\"0\">\n" +
                "     <tr>\n" +
                "       <td style=\"padding: 30px 30px 30px 60px;\">\n" +
                "        <h2 style=\"font-size: 28px; margin:0 0 20px 0; font-family:Arial;\">Transaction Notification</h2>\n" +
                "        <p style=\"font-size: 16px; margin:0 0 20px 0; font-family:Arial;\">Spedire Tech just paid  for Transaction Id  successfully delivered.</p>\n" +
                //"        <p>See you soon.</p>\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </table>\n" +
                "  \n" +
                " <!-- Footer -->\n" +
                "  <table role=\"presentation\" border=\"0\" width=\"100%\" cellspacing=\"0\">\n" +
                "      <tr>\n" +
                "          <td bgcolor=\"#F5F8FA\" style=\"padding: 30px 30px;\">\n" +
                "<hr/>"+
                "            <p style=\"margin:0 0 12px 0; font-size:16px; line-height:24px; color: #99ACC2; font-family:Arial\"> &copy; 2023 Spedire Technologies.  All rights reserved. </p>\n" +
                "            <a href=\"#\" style=\"font-size: 9px; text-transform:uppercase; letter-spacing: 1px; color: #99ACC2;  font-family:Arial;\"> Unsubscribe </a>      \n" +
                "          </td>\n" +
                "      </tr>\n" +
                "  </table> \n" +
                "</div>";

    }
}
