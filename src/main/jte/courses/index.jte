@import org.example.hexlet.dto.courses.CoursesPage
@import org.example.hexlet.util.NamedRoutes
@param CoursesPage page

@template.layout.page(
content = @`
        <h1>${page.getHeader()}</h1>
        <form action="${NamedRoutes.coursesPath()}" method="get">
            <input type="search" name="term" value="${page.getTerm()}" />
            <input type="submit" value="Search" />
        </form>
        @if(page.getCourses().isEmpty())
            <p>Пока не добавлено ни одного курса</p>
        @else

            @for(var course : page.getCourses())
               <div>
                   <h2><a href="${NamedRoutes.userPath(course.getId())}">${course.getId() + " " + course.getName()}</a></h2>
                   <p>${course.getDescription()}</p>
               </div>
            @endfor
        @endif
    <button onclick="window.location.href='${NamedRoutes.buildCoursePath()}'">Добавить новый курс</button>
`
)