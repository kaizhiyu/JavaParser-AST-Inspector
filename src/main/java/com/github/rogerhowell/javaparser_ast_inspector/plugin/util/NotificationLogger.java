package com.github.rogerhowell.javaparser_ast_inspector.plugin.util;

import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class NotificationLogger {

    private static final boolean IS_LOGGING_ENABLED_DEBUG = false;
    private static final boolean IS_LOGGING_ENABLED_ERROR = true;
    private static final boolean IS_LOGGING_ENABLED_INFO  = true;
    private static final boolean IS_LOGGING_ENABLED_TRACE = false;
    private static final boolean IS_LOGGING_ENABLED_WARN  = true;
    @NotNull
    private final Logger logger;

    @NotNull
    private final NotificationGroup notificationGroup_debug;
    @NotNull
    private final NotificationGroup notificationGroup_error;
    @NotNull
    private final NotificationGroup notificationGroup_info;
    @NotNull
    private final NotificationGroup notificationGroup_trace;
    @NotNull
    private final NotificationGroup notificationGroup_warn;


    //    public NotificationLogger(@NotNull Class<T> clazz, @NotNull String displayId) {
    public NotificationLogger(@NotNull Class<?> clazz) {
        this.logger = Logger.getInstance(clazz.getName());
        this.notificationGroup_trace = new NotificationGroup(
                clazz.getName() + " (trace)",
                NotificationDisplayType.NONE,
                IS_LOGGING_ENABLED_TRACE
        );
        this.notificationGroup_debug = new NotificationGroup(
                clazz.getName() + " (debug)",
                NotificationDisplayType.NONE,
                IS_LOGGING_ENABLED_DEBUG
        );
        this.notificationGroup_info = new NotificationGroup(
                clazz.getName() + " (information)",
                NotificationDisplayType.NONE,
                IS_LOGGING_ENABLED_INFO
        );
        this.notificationGroup_warn = new NotificationGroup(
                clazz.getName() + " (warnings)",
                NotificationDisplayType.BALLOON,
                IS_LOGGING_ENABLED_WARN
        );
        this.notificationGroup_error = new NotificationGroup(
                clazz.getName() + " (errors)",
                NotificationDisplayType.STICKY_BALLOON,
                IS_LOGGING_ENABLED_ERROR
        );
    }


    public void debug(@NotNull String messageContent) {
        this.debug(null, messageContent);
    }


    public void debug(@Nullable Project project, @NotNull String messageContent) {
        this.logger.debug(messageContent);
        this.notificationGroup_debug
                .createNotification("DEBUG", this.getSubtitle(project), messageContent, NotificationType.INFORMATION)
                .notify(project);
    }


    public void debug(@NotNull String messageContent, Throwable e) {
        this.debug(null, messageContent, e);
    }


    public void debug(@Nullable Project project, @NotNull String messageContent, Throwable e) {
        this.logger.debug(messageContent, e);
        this.notificationGroup_debug
                .createNotification("DEBUG", this.getSubtitle(project), messageContent, NotificationType.INFORMATION)
                .notify(project);
    }


    public void error(@NotNull String messageContent) {
        this.error(null, messageContent);
    }


    public void error(@Nullable Project project, @NotNull String messageContent) {
        this.logger.error(messageContent);
        this.notificationGroup_error
                .createNotification("ERROR", this.getSubtitle(project), messageContent, NotificationType.ERROR)
                .notify(project);
    }


    public void error(@NotNull String messageContent, Throwable e) {
        this.error(null, messageContent, e);
    }


    public void error(@Nullable Project project, @NotNull String messageContent, Throwable e) {
        this.logger.error(messageContent, e);
        this.notificationGroup_error
                .createNotification("ERROR", this.getSubtitle(project), messageContent, NotificationType.ERROR)
                .notify(project);
    }
//    public void trace(@NotNull String messageContent, Throwable e) {
//        this.trace(null, messageContent, e);
//    }
//    public void trace(@Nullable Project project, @NotNull String messageContent, Throwable e) {
//        final String projectName = (project == null) ? "<unspecified>" : project.getName();
//        this.logger.trace(messageContent, e);
//        notificationGroup.createNotification("Title", "Project: " + projectName(project).orElse("<unspecified>"), messageContent).notify(project);
//    }


    @NotNull
    private String getSubtitle(@Nullable final Project project) {
        return "Project: " + this.projectName(project).orElse("<unspecified>");
    }


    public void info(@NotNull String messageContent) {
        this.info(null, messageContent);
    }


    public void info(@Nullable Project project, @NotNull String messageContent) {
        this.logger.info(messageContent);
        this.notificationGroup_info
                .createNotification("INFORMATION", this.getSubtitle(project), messageContent, NotificationType.INFORMATION)
                .notify(project);
    }


    public void info(@NotNull String messageContent, Throwable e) {
        this.info(null, messageContent, e);
    }


    public void info(@Nullable Project project, @NotNull String messageContent, Throwable e) {
        this.logger.info(messageContent, e);
        this.notificationGroup_info
                .createNotification("INFORMATION", this.getSubtitle(project), messageContent, NotificationType.INFORMATION)
                .notify(project);
    }


    private Optional<String> projectName(@Nullable Project project) {
        if (project == null) {
            return Optional.empty();
        }
        return Optional.of(project.getName());
    }


    public void trace(@NotNull String messageContent) {
        this.trace(null, messageContent);
    }


    public void trace(@Nullable Project project) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];

        String location   = stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber();
        String methodName = stackTraceElement.getMethodName();
        String message    = location + " (#" + methodName + ")";

        this.trace(project, message);
    }


    public void trace(@Nullable Project project, @NotNull String messageContent) {
        this.logger.trace(messageContent);
        this.notificationGroup_trace
                .createNotification("TRACE", this.getSubtitle(project), messageContent, NotificationType.INFORMATION)
                .notify(project);
    }


    public void traceEnter() {
        this.traceEnter(null);
    }


    public void traceEnter(@Nullable Project project) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];

        String location   = stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber();
        String methodName = stackTraceElement.getMethodName();

        this.traceEnter(project, methodName, location);
    }


    public void traceEnter(@Nullable Project project, @NotNull String methodName, @NotNull String location) {
//        String messageContent    = location + " (#" + methodName + ")";
        String locationString = "#" + methodName + " @ " + location + "";

        this.logger.trace(locationString);
        this.notificationGroup_trace
                .createNotification("TRACE ENTER", locationString, this.getSubtitle(project), NotificationType.INFORMATION)
                .notify(project);
    }


    public void warn(@NotNull String messageContent) {
        this.warn(null, messageContent);
    }


    public void warn(@Nullable Project project, @NotNull String messageContent) {
        this.logger.warn(messageContent);
        this.notificationGroup_warn
                .createNotification("WARNING", this.getSubtitle(project), messageContent, NotificationType.WARNING)
                .notify(project);
    }


    public void warn(@NotNull String messageContent, Throwable e) {
        this.warn(null, messageContent, e);
    }


    public void warn(@Nullable Project project, @NotNull String messageContent, Throwable e) {
        this.logger.warn(messageContent, e);
        this.notificationGroup_warn
                .createNotification("WARNING", this.getSubtitle(project), messageContent, NotificationType.WARNING)
                .notify(project);
    }

}
