Social Media Platform

A Java-based social media application that allows users to create profiles, make posts, comment, follow other users and interact with content through likes and engagement.


Project Architecture

Data Storage

The application uses CSV files for data persistence:
- users.csv - Stores user profile information and authentication data
- posts.csv - Contains all posts made by users with metadata
- comments.csv - Stores comments associated with posts


Core Components

Interfaces

- Likeable - Common interface for Post and Comment classes, defining shared functionality for content that can be liked


Classes

CSVFileActions.java

- Handles all file I/O operations
- Manages reading/writing to CSV files
- Creates and maintains vectors of users, posts, and comments
- Exports data to appropriate CSV format

App.java

- Main application controller and command handler
- Implements user authentication validation
- Handles edge cases (null strings, empty inputs)
- Manages application lifecycle (cleanup, command execution)
- Coordinates between different classes and file operations

User.java

- Contains all user-specific functionality and commands
- User profile management (create, update, delete)
- Social features (follow/unfollow, friend management)
- Authentication and session management
- Sorting and ranking algorithms for user-related data
- Helper methods for validation and edge case handling

Post.java

- Post creation, editing, and deletion functionality
- Post engagement tracking (likes, comments)
- Content organization and filtering
- Date-based sorting and chronological ordering
- Post visibility and privacy controls

Comment.java
- 
- Comment creation and management on posts
- Comment threading and reply functionality
- Like/unlike functionality for comments
- Comment moderation features
- Chronological sorting and display


Key Features

User Management

- User registration and authentication
- Profile creation and management
- Follow/unfollow system
- Friend connections

Content Creation

- Post creation with timestamps
- Comment system with nested replies
- Like functionality for posts and comments
- Content sorting by date and engagement

Social Features

- User feed with followed content
- Most liked posts discovery
- Most commented posts tracking
- User activity analytics
- Top user rankings by engagement

Data Analytics

- Engagement metrics (likes, comments, shares)
- User activity tracking
- Content popularity algorithms
- Temporal analysis with date-based sorting


Technical Implementation

Authentication System

- All user actions require authentication validation before execution.

Data Management

- Cleanup operations ensure data consistency
- Vector-based data structures for in-memory operations
- CSV export/import for data persistence
- Sorting algorithms for content organization

Edge Case Handling

- Null and empty string validation
- Authentication state verification
- Data consistency checks
- File operation error handling

Bonus Features & Considerations

Implemented Edge Cases

- Empty Platform Scenarios: Handles cases where no posts exist for comment/like ranking
- Self-Following: Prevents users from following themselves
- Self-Unfollowing: Handles attempts to unfollow oneself
- Content Discovery: Manages empty feed scenarios for new users

Potential Platform Extensions

Privacy & Security Features

- Private Posts: Content visible only to selected friends
- Private Friend Lists: Hidden social connections
- Content Reporting: User-driven moderation for inappropriate content
- Post Archiving: Hide posts without deletion

Enhanced Social Features

- Content Sharing: Redistribute/save important posts
- Reply Threading: Comment responses and conversations
- Content Curation: Personal content collections
- Advanced Analytics: User engagement insights

Platform Scalability

- Content Categorization: Topic-based post organization
- Advanced Search: Content discovery and filtering
- Recommendation Engine: Personalized content suggestions
- Real-time Notifications: User activity updates

Usage
The application processes commands through the main App class, with authentication validation and file management handled automatically. All user interactions result in immediate CSV file updates to maintain data persistence.

Data Flow

Authentication → Command Validation → Class Method Execution → File Updates → Response Generation
CSV Reading → Vector Population → In-Memory Operations → Data Export → File Persistence

Development Notes

- Date generation follows consistent formatting standards
- Sorting algorithms maintain data integrity through auxiliary vectors
- Display functions handle edge cases for proper JSON/text formatting
- Modular design allows for easy feature extension and maintenance
