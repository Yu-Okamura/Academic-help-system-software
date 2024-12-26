Academic Help System 

Overview

This project introduces a server-based system designed to manage user accounts, roles, and help articles. The software facilitates collaboration, leadership, and efficient user management while providing robust tools for creating, organizing, and searching help articles. The system includes user roles such as Admin, Instructor, and Student, each with specific privileges and responsibilities.

Features

1. User Management
   - Secure account creation and login.
   - Role-based access control (Admin, Instructor, Student).
   - Password reset and account deletion features.
   - Invite-based system for adding new users.

2. Help Article System
   - Add, update, delete, backup, and restore articles.
   - Categorize articles by groups or topics.
   - Support for keyword-based article searches.
   - Encrypted articles with restricted access for privacy.

3. Role-based Permissions
   - Admins: Full control over user management and articles.
   - Instructors: Limited control over articles and user groups.
   - Students: Ability to search articles and send queries.

4. Data Security
   - Enhanced encryption for sensitive articles.
   - Access protocols for controlled visibility.

5. Messaging System
   - Generic and specific message categories for troubleshooting or queries.
   - Messaging capabilities between students and the help system.

Architecture

The system is built using JavaFX for the user interface and MySQL for data storage. Key functionalities include:

- User authentication and role management.
- Article database operations (create, search, encrypt, backup/restore).
- Scene-based navigation in JavaFX.

User Stories

This project addresses a wide range of user needs, including but not limited to:

- Creating and managing accounts with specific roles.
- Organizing articles by topic and keyword for easy retrieval.
- Managing access to encrypted content based on user roles.
- Sending messages for system support and troubleshooting.

Technical Details

Main Components

- JavaFX Application: Provides the user interface for account management and article operations.
- MySQL Database: Stores user accounts, roles, and articles.
- Encryption Utilities: Ensures data security for sensitive content.

Usage

1. Setup: Clone the repository and set up the database using the provided SQL scripts.
2. Run: Launch the application using a JavaFX-compatible IDE.
3. Admin Tasks:
   - Invite users and manage roles.
   - Add and organize articles.
   - Backup and restore article data.
4. User Tasks:
   - Students can search for articles and send queries.
   - Instructors can manage articles and user groups.

Contributors

- Yu Okamura
- Max Wolf
- Ananya Arora
- Yu Okamura
- Ben Moriarty
- Princess Mariah Taylor

License

This software is licensed and intended for personal use only. Any reuse, copying, or distribution of any part of this program without prior written consent is strictly prohibited.

