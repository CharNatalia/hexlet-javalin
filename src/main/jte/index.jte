@import org.example.hexlet.util.NamedRoutes

@template.layout.page(
content = @`
  <div>
    <a href="${NamedRoutes.usersPath()}">Пользователи</a>
  </div>

  <div>
    <a href="${NamedRoutes.coursesPath()}">Курсы</a>
  </div>

`
)
