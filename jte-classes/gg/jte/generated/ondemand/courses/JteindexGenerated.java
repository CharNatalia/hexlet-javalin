package gg.jte.generated.ondemand.courses;
import org.example.hexlet.dto.courses.CoursesPage;
public final class JteindexGenerated {
	public static final String JTE_NAME = "courses/index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,7,7,7,7,8,8,10,10,11,11,13,13,13,13,13,13,13,14,14,14,16,16,17,17,19,19,19,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, CoursesPage page) {
		jteOutput.writeContent("<html>\n<head>\n    <title>Хекслет</title>\n</head>\n<body>\n<h1>");
		jteOutput.setContext("h1", null);
		jteOutput.writeUserContent(page.getHeader());
		jteOutput.writeContent("</h1>\n");
		if (page.getCourses().isEmpty()) {
			jteOutput.writeContent("\n    <p>Пока не добавлено ни одного курса</p>\n");
		} else {
			jteOutput.writeContent("\n    ");
			for (var course : page.getCourses()) {
				jteOutput.writeContent("\n        <div>\n            <h2><a href=\"/courses/");
				jteOutput.setContext("a", "href");
				jteOutput.writeUserContent(course.getId());
				jteOutput.setContext("a", null);
				jteOutput.writeContent("\">");
				jteOutput.setContext("a", null);
				jteOutput.writeUserContent(course.getName());
				jteOutput.writeContent("</a></h2>\n            <p>");
				jteOutput.setContext("p", null);
				jteOutput.writeUserContent(course.getDescription());
				jteOutput.writeContent("</p>\n        </div>\n    ");
			}
			jteOutput.writeContent("\n");
		}
		jteOutput.writeContent("\n</body>\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		CoursesPage page = (CoursesPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
