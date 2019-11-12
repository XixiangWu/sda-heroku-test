package au.edu.unimelb.cis.swen90007.itsms.factory;

public class FrontEndFactory {

    public static String navBarGenerator(String username) {
        String nav = "<!-- Navbars -->\n" +
                "        <nav class=\"navbar navbar-expand-lg navbar-light bg-light mb-4\">\n" +
                "            <a class=\"navbar-brand\" href=\"#\">IT Support Management System</a>\n" +
                "            <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarNavDropdown-7\" aria-controls=\"navbarNavDropdown-7\"\n" +
                "                    aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n" +
                "                <span class=\"navbar-toggler-icon\"></span>\n" +
                "            </button>\n" +
                "            <div class=\"collapse navbar-collapse\" id=\"navbarNavDropdown-7\">\n" +
                "                <ul class=\"navbar-nav mr-auto\">\n" +
                "                    <li class=\"nav-item active\">\n" +
                "                        <a class=\"nav-link\" href=\"/viewIssues\">View issues\n" +
                "                            <span class=\"sr-only\">(current)</span>\n" +
                "                        </a>\n" +
                "                    </li>\n" +
                "                    <li class=\"nav-item\">\n" +
                "                        <a class=\"nav-link\" href=\"/submit\">Submit new issue</a>\n" +
                "                    </li>\n" +
                "                    <li class=\"nav-item\">\n" +
                "                        <a class=\"nav-link\" href=\"/viewAppointments\">View appointments</a>\n" +
                "                    </li>\n" +
                "                    <li class=\"nav-item\">\n" +
                "                        <a class=\"nav-link\" href=\"/submitAppointment\">Submit new appointment</a>\n" +
                "                    </li>\n" +
                "                    <li class=\"nav-item\">\n" +
                "                        <a class=\"nav-link\" href=\"/login\">Login</a>\n" +
                "                    </li>\n" +
                "                </ul>\n" +
                "                <ul class=\"navbar-nav\">\n" +
                "                    Hello, " + username + "\n" +
                "                </ul>\n" +
                "            </div>\n" +
                "        </nav>\n";
        return nav;
    }

    public static String headerGenerator() {
        String header = "" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n" +
                "    <title>Shards Demo - A free and modern UI toolkit for web makers</title>\n" +
                "    <meta name=\"description\" content=\"A free and modern UI toolkit for web makers based on the popular Bootstrap 4 framework.\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "    <link href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\">\n" +
                "    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css\">\n" +
                "    <link rel=\"stylesheet\" href=\"css/shards.min.css?v=3.0.0\">\n" +
                "    <link rel=\"stylesheet\" href=\"css/shards-demo.min.css?v=3.0.0\">\n" +
                "</head>";
        return header;
    }

    public static String scriptGenerator() {
        String script = "\n" +
                "<script async defer src=\"https://buttons.github.io/buttons.js\"></script>\n" +
                "<script async src=\"//platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>\n" +
                "<script src=\"https://code.jquery.com/jquery-3.2.1.min.js\" integrity=\"sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4=\"\n" +
                "        crossorigin=\"anonymous\"></script>\n" +
                "<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js\" integrity=\"sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4\"\n" +
                "        crossorigin=\"anonymous\"></script>\n" +
                "<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\" integrity=\"sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM\" crossorigin=\"anonymous\"></script>\n" +
                "<script src=\"js/shards.min.js\"></script>\n" +
                "<script src=\"js/demo.min.js\"></script>\n" +
                "</body>\n" +
                "</html>\n";
        return script;
    }

    private static final String appointmentAcceptedBadge = "<span class=\"badge mr-3 badge-primary\">Accepted</span>";
    private static final String appointmentWaitingBadge  = "<span class=\"badge mr-3 badge-pill badge-outline-danger\">Waiting</span>";
    private static final String appointmentResolvedBadge = "<span class=\"badge mr-3 badge-pill badge-success\">Resolved</span>";

    public static String appointmentBadgeGenerator(String status) {
        switch (status) {
            case "ACCEPTED":
                return appointmentAcceptedBadge;
            case "WAITING":
                return appointmentWaitingBadge;
            case "RESOLVED":
                return appointmentResolvedBadge;
        }
        return null;
    }

}
