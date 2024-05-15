<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Welcome</title>
        <link rel="stylesheet" href="css/welcome.css">
    </head>
    <body>
        <div class="welcome-container">
            <h1 class="welcome">Welcome<br>${user.firstName} ${user.lastName}</h1>
            <div id="countdown">5</div>
        </div>

        <script>
            document.addEventListener("DOMContentLoaded", function() {
                var count = 5;
                var countdownDisplay = document.getElementById("countdown");
                var countdown = setInterval(function() {
                    countdownDisplay.innerHTML = count;
                    count--;
                    if (count < 0) {
                        clearInterval(countdown);
                        window.location.href = "main.jsp";
                    }
                }, 1000); // Update every 1 second
            });
        </script>
    </body>
</html>