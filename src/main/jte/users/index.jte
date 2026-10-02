@import org.example.hexlet.dto.courses.CoursesPage
@import org.example.hexlet.dto.users.UsersPage
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
    <button onclick="window.location.href='/users/build'">Добавить пользователя</button>
`
)