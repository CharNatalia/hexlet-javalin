@import gg.jte.Content
@import org.example.hexlet.util.NamedRoutes
@param Content content

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>My site</title>
</head>

<body>

<header>
    <h1>My site</h1>
    <a href="${NamedRoutes.rootPath()}">Главная</a>
</header>

<main>
    ${content}
</main>

<footer>
    <a href="https://github.com/CharNatalia">My GitHub</a>
</footer>

</body>
</html>