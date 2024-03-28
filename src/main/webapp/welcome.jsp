
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="css/welcome.css">
    </head>

    <body>

        <h1 class="welcome">Welcome<br>${user.firstName} ${user.lastName}</h1>

        <!-- Countdown display -->
        <div id="countdown"></div>

        <!-- Floating images -->
        <img src="images/thermostats.png" class="floating-image" alt="Image 1" style="width: 300px; height: 300px;">
        <img src="images/smartWatch.png" class="floating-image" alt="Image 2" style="width: 300px; height: 300px;">
        <img src="images/smartBulb.png" class="floating-image" alt="Image 3" style="width: 300px; height: 300px;">
        <img src="images/raspberryPi.png" class="floating-image" alt="Image 5" style="width: 300px; height: 300px;">
        <img src="images/nodeMCU.png" class="floating-image" alt="Image 6" style="width: 300px; height: 300px;">

        <script>
            // JavaScript for countdown and redirection
            document.addEventListener("DOMContentLoaded", function() {
                var count = 5;
                var countdownDisplay = document.createElement("div");
                countdownDisplay.id = "countdown";
                document.body.appendChild(countdownDisplay); // Append countdown display to body
                var countdown = setInterval(function() {
                    countdownDisplay.innerHTML = count + "%"; // Concatenate "%" sign
                    count--;
                    if (count < 0) {
                        clearInterval(countdown);
                        // Redirect to main page or perform other actions
                        window.location.href = "main.jsp";
                    }
                }, 500); // Update every 0.5 second
            });
        </script>

        <script>
            // JavaScript for image animation
            document.addEventListener("DOMContentLoaded", function() {
                const images = document.querySelectorAll(".floating-image");

                images.forEach(function(image) {
                    // Calculate the initial position to start at the center of the screen
                    let xPos = (window.innerWidth - image.width) / 2;
                    let yPos = (window.innerHeight - image.height) / 2;

                    // Set random direction for each image
                    let xDirection = Math.random() < 0.5 ? -1 : 1;
                    let yDirection = Math.random() < 0.5 ? -1 : 1;

                    // Set random speed for each image
                    let speed = Math.random() + 1.0; // Random speed between 0.1 and 1 pixel per frame

                    // Animate the image
                    function animate() {
                        xPos += speed * xDirection;
                        yPos += speed * yDirection;

                        // Update image position
                        image.style.position = "fixed";
                        image.style.left = xPos + "px";
                        image.style.top = yPos + "px";

                        requestAnimationFrame(animate);
                    }

                    animate();
                });
            });
        </script>

    </body>
</html>
