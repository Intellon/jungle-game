const canvas = document.getElementById('gameCanvas');
const ctx = canvas.getContext('2d');

// Hintergrund PNG
const backgroundImage = new Image();
backgroundImage.src = 'assets/images/background.png';  // Pfad zu deinem Hintergrund-PNG

// Raubtier SVG
const raubtierImage = new Image();
raubtierImage.src = 'assets/images/raubtier.svg';  // Pfad zu deinem Raubtier-SVG

// Roboter SVG
const roboterImage = new Image();
roboterImage.src = 'assets/images/roboter.svg';  // Pfad zu deinem Roboter-SVG

// Canvas an die Größe des Fensters anpassen
function resizeCanvas() {
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
}

// Raubtier Charakter
const raubtier = {
    x: 100,
    y: canvas.height / 2 - 25,
    width: 50,
    height: 50,
    speed: 5,
    dx: 0,
    dy: 0,
    points: 100,  // Startpunkte des Spielers
    lastHitTime: 0,  // Zeitpunkt des letzten Treffers
    hitCooldown: 1000  // 1 Sekunde Cooldown zwischen Kollisionen
};

// Roboter-Array
const roboter = [];
const roboterCount = 10;
const roboterWidth = 40;
const roboterHeight = 40;
const roboterSpeed = 2;  // Geschwindigkeit der Roboter

// Pfeil-Array
const arrows = [];
const arrowWidth = 10;
const arrowHeight = 5;
const arrowSpeed = 7;

// Münzen
let coins = 0;

// Spielstatus
let gameRunning = false;
let gameOver = false;
let showHitMessage = false;  // Zeigt an, ob die Treffer-Nachricht angezeigt wird
let hitMessageTime = 0;  // Zeitstempel, wann die Nachricht angezeigt wurde
const hitMessageDuration = 1000;  // Dauer der Treffer-Nachricht in Millisekunden (1 Sekunde)

// Steuerung
document.addEventListener('keydown', keyDown);
document.addEventListener('keyup', keyUp);

function keyDown(e) {
    if (!gameRunning && !gameOver) {
        gameRunning = true;  // Spiel startet, wenn eine Taste gedrückt wird
        updateGame();  // Startet das Update-Loop, wenn das Spiel startet
    }

    if (e.key === 'ArrowRight') {
        raubtier.dx = raubtier.speed;
    } else if (e.key === 'ArrowLeft') {
        raubtier.dx = -raubtier.speed;
    } else if (e.key === 'ArrowUp') {
        raubtier.dy = -raubtier.speed;
    } else if (e.key === 'ArrowDown') {
        raubtier.dy = raubtier.speed;
    } else if (e.key === ' ') {
        shootArrow();  // Pfeil abfeuern
    }
}

function keyUp(e) {
    if (e.key === 'ArrowRight' || e.key === 'ArrowLeft') {
        raubtier.dx = 0;
    }
    if (e.key === 'ArrowUp' || e.key === 'ArrowDown') {
        raubtier.dy = 0;
    }
}

// Roboter erstellen
function createRoboter() {
    for (let i = 0; i < roboterCount; i++) {
        const x = Math.random() * (canvas.width - roboterWidth);
        const y = Math.random() * (canvas.height - roboterHeight);
        const directionX = Math.random() < 0.5 ? 1 : -1;  // Zufällige Richtung
        const directionY = Math.random() < 0.5 ? 1 : -1;
        roboter.push({ x, y, width: roboterWidth, height: roboterHeight, hit: false, dx: roboterSpeed * directionX, dy: roboterSpeed * directionY });
    }
}

// Pfeil schießen
function shootArrow() {
    const arrow = {
        x: raubtier.x + raubtier.width,
        y: raubtier.y + raubtier.height / 2 - arrowHeight / 2,
        width: arrowWidth,
        height: arrowHeight,
        dx: arrowSpeed
    };
    arrows.push(arrow);
}

// Pfeile zeichnen (blau)
function drawArrows() {
    arrows.forEach((arrow, index) => {
        ctx.fillStyle = 'blue';  // Pfeile blau färben
        ctx.fillRect(arrow.x, arrow.y, arrow.width, arrow.height);
        // Pfeil bewegen
        arrow.x += arrow.dx;

        // Pfeil entfernen, wenn er den Bildschirm verlässt
        if (arrow.x > canvas.width) {
            arrows.splice(index, 1);
        }
    });
}

// Hintergrund zeichnen mit transparenz
function drawBackground() {
    ctx.globalAlpha = 0.7;  // Setze die Transparenz für den Hintergrund auf 70%
    ctx.drawImage(backgroundImage, 0, 0, canvas.width, canvas.height);  // Hintergrundbild zeichnen
    ctx.globalAlpha = 1.0;  // Setze die Transparenz wieder auf 100% für andere Elemente
}

// Raubtier zeichnen (mit SVG)
function drawRaubtier() {
    ctx.drawImage(raubtierImage, raubtier.x, raubtier.y, raubtier.width, raubtier.height);  // Raubtier-SVG zeichnen
}

// Roboter zeichnen und bewegen (mit SVG)
function drawRoboter() {
    roboter.forEach(r => {
        if (!r.hit) {
            ctx.drawImage(roboterImage, r.x, r.y, r.width, r.height);  // Roboter-SVG zeichnen

            // Roboter bewegen
            r.x += r.dx;
            r.y += r.dy;

            // Roboter vom Rand abprallen lassen
            if (r.x < 0 || r.x + r.width > canvas.width) {
                r.dx *= -1;
            }
            if (r.y < 0 || r.y + r.height > canvas.height) {
                r.dy *= -1;
            }
        }
    });
}

// Münzen und Punktestand anzeigen
function drawStats() {
    ctx.fillStyle = 'black';
    ctx.font = '20px Arial';
    ctx.fillText(`Münzen: ${coins}`, 10, 20);
    ctx.fillText(`Punkte: ${raubtier.points}`, 10, 50);
}

// Treffer-Nachricht anzeigen
function drawHitMessage() {
    if (showHitMessage) {
        ctx.fillStyle = 'red';
        ctx.font = '30px Arial';
        ctx.fillText('Treffer!', canvas.width / 2 - 50, canvas.height / 2);
    }
}

// Spiel-Ende Nachricht anzeigen
function drawGameOver() {
    ctx.fillStyle = 'black';
    ctx.font = '30px Arial';
    ctx.fillText('Spiel beendet!', canvas.width / 2 - 100, canvas.height / 2);
}

// Raubtier bewegen
function moveRaubtier() {
    raubtier.x += raubtier.dx;
    raubtier.y += raubtier.dy;

    // Grenzen prüfen
    if (raubtier.x < 0) raubtier.x = 0;
    if (raubtier.x + raubtier.width > canvas.width) raubtier.x = canvas.width - raubtier.width;
    if (raubtier.y < 0) raubtier.y = 0;
    if (raubtier.y + raubtier.height > canvas.height) raubtier.y = canvas.height - raubtier.height;
}

// Kollision erkennen (Pfeil gegen Roboter und Roboter gegen Raubtier)
function detectCollision() {
    const currentTime = Date.now();  // Aktuelle Zeit in Millisekunden

    arrows.forEach((arrow, arrowIndex) => {
        roboter.forEach(r => {
            if (!r.hit && arrow.x < r.x + r.width &&
                arrow.x + arrow.width > r.x &&
                arrow.y < r.y + r.height &&
                arrow.y + arrow.height > r.y) {
                r.hit = true;  // Roboter wird getroffen
                coins += 1;    // Münzen sammeln
                arrows.splice(arrowIndex, 1); // Pfeil entfernen
            }
        });
    });

    // Kollision zwischen Roboter und Raubtier (mit 1 Sekunde Cooldown)
    roboter.forEach(r => {
        if (!r.hit && raubtier.x < r.x + r.width &&
            raubtier.x + raubtier.width > r.x &&
            raubtier.y < r.y + r.height &&
            raubtier.y + raubtier.height > r.y) {

            if (currentTime - raubtier.lastHitTime >= raubtier.hitCooldown) {
                raubtier.points -= 10;  // Punkteabzug um 10
                raubtier.lastHitTime = currentTime;  // Zeitpunkt des letzten Treffers aktualisieren

                // Treffer-Nachricht anzeigen
                showHitMessage = true;
                hitMessageTime = currentTime;  // Zeit des Treffers speichern
            }
        }
    });
}

// Spielstatus prüfen (Spielende)
function checkGameOver() {
    const allRobotsHit = roboter.every(r => r.hit);
    if (allRobotsHit || raubtier.points <= 0) {
        gameOver = true;
    }
}

// Treffer-Nachricht verwalten
function manageHitMessage() {
    const currentTime = Date.now();
    if (showHitMessage && currentTime - hitMessageTime > hitMessageDuration) {
        showHitMessage = false;  // Treffer-Nachricht nach Ablauf ausblenden
    }
}

// Spiel aktualisieren
function updateGame() {
    if (gameOver) {
        drawGameOver();
        return;  // Spielende, keine weitere Aktualisierung
    }

    ctx.clearRect(0, 0, canvas.width, canvas.height);

    drawBackground();  // Hintergrundbild zuerst zeichnen
    drawRaubtier();
    drawRoboter();
    drawArrows();  // Pfeile in Blau zeichnen
    drawStats();
    drawHitMessage();  // Treffer-Nachricht anzeigen

    moveRaubtier();
    detectCollision();
    manageHitMessage();  // Treffer-Nachricht verwalten
    checkGameOver();

    requestAnimationFrame(updateGame);  // Ständiges Update
}

// Bei Fenstergrößenänderung das Canvas anpassen
window.addEventListener('resize', resizeCanvas);

// Initiales Anpassen des Canvas
resizeCanvas();
createRoboter();
