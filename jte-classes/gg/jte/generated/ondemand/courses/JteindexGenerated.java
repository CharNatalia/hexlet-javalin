package gg.jte.generated.ondemand.courses;
import org.example.hexlet.dto.courses.CoursesPage;
@SuppressWarnings("unchecked")
@javax.annotation.processing.Generated("gg.jte.TemplateEngine")
public final class JteindexGenerated {
	public static final String JTE_NAME = "courses/index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,1,1,3,3,5,5,6,6,6,8,8,8,8,8,8,8,8,8,11,11,13,13,15,15,17,17,17,17,17,17,17,18,18,18,20,20,21,21,23,23,23,23,23,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, CoursesPage page) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n        <h1>");
				jteOutput.setContext("h1", null);
				jteOutput.writeUserContent(page.getHeader());
				jteOutput.writeContent("</h1>\n        <form action=\"/courses\" method=\"get\">\n            <input type=\"search\" name=\"term\"");
				var __jte_html_attribute_0 = page.getTerm();
				if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
					jteOutput.writeContent(" value=\"");
					jteOutput.setContext("input", "value");
					jteOutput.writeUserContent(__jte_html_attribute_0);
					jteOutput.setContext("input", null);
					jteOutput.writeContent("\"");
				}
				jteOutput.writeContent(" />\n            <input type=\"submit\" value=\"Search\" />\n        </form>\n        ");
				if (page.getCourses().isEmpty()) {
					jteOutput.writeContent("\n            <p>Пока не добавлено ни одного курса</p>\n        ");
				} else {
					jteOutput.writeContent("\n\n            ");
					for (var course : page.getCourses()) {
						jteOutput.writeContent("\n               <div>\n                   <h2><a href=\"/courses/");
						jteOutput.setContext("a", "href");
						jteOutput.writeUserContent(course.getId());
						jteOutput.setContext("a", null);
						jteOutput.writeContent("\">");
						jteOutput.setContext("a", null);
						jteOutput.writeUserContent(course.getId() + " " + course.getName());
						jteOutput.writeContent("</a></h2>\n                   <p>");
						jteOutput.setContext("p", null);
						jteOutput.writeUserContent(course.getDescription());
						jteOutput.writeContent("</p>\n               </div>\n            ");
					}
					jteOutput.writeContent("\n        ");
				}
				jteOutput.writeContent("\n    <button onclick=\"window.location.href='/courses/build'\">Добавить новый курс</button>\n");
			}
		});
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		CoursesPage page = (CoursesPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
