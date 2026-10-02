@import org.example.hexlet.dto.users.UsersPage
@import org.example.hexlet.util.NamedRoutes
@param UsersPage page

@template.layout.page(
content = @`
    <h1>Пользователи</h1>
    @if(page.getUsers().isEmpty())
        <p>Пока не добавлено ни одного пользователя</p>
    @else

        @for(var user : page.getUsers())
            <div>
                <h2>${user.getName()}</h2>
                <p>${user.getEmail()}</p>
            </div>
        @endfor
    @endif
    <button onclick="window.location.href='${NamedRoutes.buildUserPath()}'">Добавить пользователя</button>
`
)