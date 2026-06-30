package projects.icore.CoursePortal.dto;

public record CourseRequest(
        String code,
        String name,
        String description,
        int credits
) {
}
