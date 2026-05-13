package pk.wieik.it_project.model;

import jakarta.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Tools {
    public static String getTemplate(String file, ServletContext context) throws IOException {
        StringBuffer output = new StringBuffer("");
        String text = "";
        InputStream is = context.getResourceAsStream("/WEB-INF/view/" + file);
        if (is != null) {
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(isr);
            while ((text = reader.readLine()) != null) {
                output.append(text).append("\n");
            }
        } else output.append("No " + file + " file");
        return output.toString();
    }

    public static String fill(String template, String tag,
                              String file, ServletContext context) throws IOException {
        StringBuffer output = new StringBuffer("");
        String text = "";
        InputStream is = context.getResourceAsStream("/WEB-INF/view/" + file);
        if (is != null) {
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(isr);
            while ((text = reader.readLine()) != null) {
                output.append(text).append("\n");
            }
        } else output.append("No " + file + " file");

        return template.replace("[[" + tag + "]]", output.toString());
    }

    public static int parseInteger(String input, int def) {
        int output = def;
        try {
            output = Integer.parseInt(input);
        } catch (NumberFormatException nfe) {
            output = def;
        }
        return output;
    }

    public static String parsePage(String input, String valid)
    {
        String output = "main";
        String[] pages = valid.split(";");
        if (input==null) input="main";

        for (String proper: pages)
        {
            if (input.equals(proper)) {
                output = input;
                return output;
            }
        }
        return output;
    }

    public static String injectScriptsAndOnload(String template, String onloadEvent, String... jsFiles) {
        StringBuilder scriptTags = new StringBuilder();

        if (jsFiles != null) {
            for (String jsFile : jsFiles) {
                if (jsFile != null && !jsFile.isEmpty()) {
                    scriptTags.append("<script src=\"").append(jsFile).append("\"></script>\n");
                }
            }
        }

        String onloadAttr = (onloadEvent != null) ? onloadEvent : "";

        template = template.replace("[[SCRIPTS]]", scriptTags.toString());
        template = template.replace("[[ONLOAD]]", onloadAttr);

        return template;
    }
}
