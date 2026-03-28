PixelPeek by Gorvanatan

A JavaFX desktop app that takes a single search word, finds a photo from the Unsplash using an API, and then reveals detailed metadata about that photo.

What It Does

1. You type one word into the search bar (e.g. `ocean`, `city`, `forest`)
2. PixelPeek fetches a photo from Unsplash
3. The photo fades onto the screen
4. After 2 seconds, a details panel appears showing:
   - 📐 Image dimensions (width × height in pixels)
   - 🎨 Dominant colour (hex code + colour swatch)
   - 🖼 File format
   - 📝 Photo description
   - 📸 Photographer name
   - 🔗 Source URL
5. After 5 seconds, a **Search Again** button appears (if you're not patient the button is invisble so you can click it before it even appears!)

Tech Used

| Tech | Purpose |

| Java 17 | Core language |
| JavaFX 21 | UI framework (scenes, animations, layout) |
| Unsplash API | Photo search + metadata |
| Java HttpClient | HTTP requests (built into Java 11+) |
| Gson 2.10 | JSON parsing of API responses |
| Maven | Build tool and dependency management |

Setup Instructions - Anyone can use this but must do some small things first!

What you need 

- Java 17 or higher ([download](https://adoptium.net/))
- Maven ([download](https://maven.apache.org/))
- A free Unsplash Developer account

To use the API 

1. Get a free Unsplash API key

1. Go to [unsplash.com/developers](https://unsplash.com/developers)
2. Click **Your apps → New Application**
3. Accept the terms and fill in the form
4. Copy your **Access Key**

2. Add your API key

Open `src/main/java/com/pixelpeek/UnsplashService.java` and replace:

```java
private static final String ACCESS_KEY = "YOUR_UNSPLASH_ACCESS_KEY";
```

with your actual key.

### 3. Clone and run

```bash
git clone https://github.com/YOUR_USERNAME/pixelpeek.git
cd pixelpeek
mvn javafx:run
```

That's it — the app window will open.

Using it!

Screen 1 — Search

Type any single word into the search bar and press Enter.

![Search screen — just a simple centered search bar on a dark background] 

Screen 2 — Result

The matching photo appears, then after a short pause the details panel fades in below it. After 5 seconds total, the **Search Again** button slides up from the bottom.

![Result screen — photo with metadata panel and Search Again button]

The result screen is scrollable, so even tall images and long descriptions are fully accessible.

## AI Acknowledgment

This project was built with assistance from AI tools in the following ways:

- **Claude (Anthropic):** Used to scaffold the initial project structure, suggest the JavaFX animation approach (`FadeTransition`, `TranslateTransition`), and help design the layout of the result screen based on a hand-drawn wireframe. Also used to draft and review this README.

- **Claude (Anthropic):** Helped debug the JavaFX module system configuration in `module-info.java` and resolve class-not-found issues when running with the Maven JavaFX plugin.

All code was reviewed, tested, and understood before being included. The core logic — API integration, scene transitions, and UI structure — was implemented and validated by the developer.

---

## License

MIT — free to use and modify.
