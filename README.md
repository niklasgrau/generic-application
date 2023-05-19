# generic-application

Diese Anwendung ist im Rahmen der Bachelorarbeit an der Fachhochschule Südwestfalen entstanden. Das Thema der Arbeit
lautet "Entwicklung einer generativen und datengetriebenen Weboberfläche sowie einer generischen,
REST-basierten Zugriffsschnittstelle für CRUD-Operationen - am Beispiel eines ERP-Systems". Die Umsetzung erfolgte als
Gruppenarbeit.

Die Anwendung besteht aus einem Spring-Backend und einem Vue-Frontend. Der Quellcode des Projekts beinhaltet ein
Framework zum Generieren einer lauffähigen Webanwendung, basierend auf XML-Metadaten. Zusätzlich liegen
Fachkonzeptklassen eines ERP-Systems vor. Die Metadaten
in `src/main/resources/de.fhswf.genericapplication/metadata/Metadata.xml` definieren die nötigen Informationen zur
exemplarischen Generierung eines ERP-Systems mithilfe des Frameworks. Das Backendsystem bietet drei Schnittstellen zum
Abrufen der Metadaten, Dateien und Entitäten.

---
This application was developed as part of the bachelor thesis at the University of Applied Sciences Südwestfalen. The
topic of the thesis
is "Development of a generative and data-driven web interface as well as a generic,
REST-based access interface for CRUD operations - using the example of an ERP system". The implementation was done as a
group work.

The application consists of a Spring backend and a Vue frontend. The source code of the project contains a
framework for generating an executable web application based on XML metadata. In addition,
concept classes of an ERP system are available. The metadata
in `src/main/resources/de.fhswf.genericapplication/metadata/Metadata.xml` define the necessary information for the
exemplary generation of an
exemplary generation of an ERP system with the help of the framework. The backend system offers three interfaces for
retrieve the metadata, files and entities.

# Requirements

- Java JDK 17 (e.g. openjdk-17)
- Node Package Manager (NPM)
- Maven

# Setup

Setup is oriented on the use of the IntelliJ IDEA.

1. Clone this repository manually
2. Checkout "main" branch
3. Open Projekt via `File` -> `Open`
4. Load Maven dependencies
5. Navigate via terminal to `src/main/ui`
6. Run `npm install` command

or

1. Clone repository directly from IntelliJ
2. Open `File` -> `New...` -> `Project from Version Control`
3. Select repository and clone
4. Load Maven dependencies
5. Navigate via terminal to `src/main/ui`
6. Run `npm install` command

# Run the application

1. Start backend application based on `src/main/java/de.fhswf.genericapplication/GenericApplication.java`
2. Start frontend application from `src/main/ui` with `npm run dev`

# Information for local testing

- The application initialises the same set of data on startup with a h2 database
- To test the REST interface, you can use the http files in "resources/api"
- An interface for the H2 database is available via `http://localhost:8080/console`