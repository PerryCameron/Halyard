package com.ecsail.gui.dialogues;

import com.sun.javafx.scene.control.skin.resources.ControlResources;
import javafx.beans.InvalidationListener;
import javafx.beans.NamedArg;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

    public class HalyardAlert extends Dialog<ButtonType> {
        private WeakReference<DialogPane> dialogPaneRef;
        private boolean installingDefaults;
        private boolean hasCustomButtons;
        private boolean hasCustomTitle;
        private boolean hasCustomHeaderText;
        private final InvalidationListener headerTextListener;
        private final InvalidationListener titleListener;
        private final ListChangeListener<ButtonType> buttonsListener;
        private final ObjectProperty<HalyardAlert.AlertType> alertType;

        public HalyardAlert(@NamedArg("alertType") HalyardAlert.AlertType alertType) {
            this(alertType, "");
        }

        public HalyardAlert(@NamedArg("alertType") HalyardAlert.AlertType alertType, @NamedArg("contentText") String contentText, ButtonType ... buttons) {
            this.installingDefaults = false;
            this.hasCustomButtons = false;
            this.hasCustomTitle = false;
            this.hasCustomHeaderText = false;
            this.headerTextListener = (o) -> {
                if (!this.installingDefaults) {
                    this.hasCustomHeaderText = true;
                }
            };
            this.titleListener = (o) -> {
                if (!this.installingDefaults) {
                    this.hasCustomTitle = true;
                }

            };
            this.buttonsListener = (change) -> {
                if (!this.installingDefaults) {
                    this.hasCustomButtons = true;
                }

            };
            this.alertType = new SimpleObjectProperty<HalyardAlert.AlertType>((HalyardAlert.AlertType)null) {
                final String[] styleClasses = new String[]{"information", "warning", "error", "confirmation"};

                protected void invalidated() {
                    String newTitle = "";
                    String newHeader = "";
                    String styleClass = "";
                    ButtonType[] newButtons = new ButtonType[]{ButtonType.OK};
                    switch(getAlertType()) {
                        case NONE:
                            newButtons = new ButtonType[0];
                            break;
                        case INFORMATION:
                            newTitle = ControlResources.getString("Dialog.info.title");
                            newHeader = ControlResources.getString("Dialog.info.header");
                            styleClass = "information";
                            break;
                        case WARNING:
                            newTitle = ControlResources.getString("Dialog.warning.title");
                            newHeader = ControlResources.getString("Dialog.warning.header");
                            styleClass = "warning";
                            break;
                        case ERROR:
                            newTitle = ControlResources.getString("Dialog.error.title");
                            newHeader = ControlResources.getString("Dialog.error.header");
                            styleClass = "error";
                            break;
                        case CONFIRMATION:
                            newTitle = ControlResources.getString("Dialog.confirm.title");
                            newHeader = ControlResources.getString("Dialog.confirm.header");
                            styleClass = "confirmation";
                            newButtons = new ButtonType[]{ButtonType.OK, ButtonType.CANCEL};
                    }

                    HalyardAlert.this.installingDefaults = true;
                    if (!HalyardAlert.this.hasCustomTitle) {
                        HalyardAlert.this.setTitle(newTitle);
                    }

                    if (!HalyardAlert.this.hasCustomHeaderText) {
                        HalyardAlert.this.setHeaderText(newHeader);
                    }

                    if (!HalyardAlert.this.hasCustomButtons) {
                        HalyardAlert.this.getButtonTypes().setAll(newButtons);
                    }

                    DialogPane dialogPane = HalyardAlert.this.getDialogPane();
                    if (dialogPane != null) {
                        List<String> toRemove = new ArrayList(Arrays.asList(this.styleClasses));
                        toRemove.remove(styleClass);
                        dialogPane.getStyleClass().removeAll(toRemove);
                        if (!dialogPane.getStyleClass().contains(styleClass)) {
                            dialogPane.getStyleClass().add(styleClass);
                        }
                    }

                    HalyardAlert.this.installingDefaults = false;
                }
            };
            DialogPane dialogPane = this.getDialogPane();
            dialogPane.setContentText(contentText);

            this.getDialogPane().getStyleClass().add("alert");
            this.dialogPaneRef = new WeakReference(dialogPane);
            this.hasCustomButtons = buttons != null && buttons.length > 0;
            if (this.hasCustomButtons) {
                ButtonType[] var5 = buttons;
                int var6 = buttons.length;

                for(int var7 = 0; var7 < var6; ++var7) {
                    ButtonType btnType = var5[var7];
                    dialogPane.getButtonTypes().addAll(new ButtonType[]{btnType});
                }
            }

            this.setAlertType(alertType);
            this.dialogPaneProperty().addListener((o) -> {
                this.updateListeners();
            });
            this.titleProperty().addListener(this.titleListener);
            this.updateListeners();
        }

        public final HalyardAlert.AlertType getAlertType() {
            return (HalyardAlert.AlertType)this.alertType.get();
        }

        public final void setAlertType(HalyardAlert.AlertType alertType) {
            this.alertType.setValue(alertType);
        }

        public final ObjectProperty<HalyardAlert.AlertType> alertTypeProperty() {
            return this.alertType;
        }

        public final ObservableList<ButtonType> getButtonTypes() {
            return this.getDialogPane().getButtonTypes();
        }

        private void updateListeners() {
            DialogPane oldPane = (DialogPane)this.dialogPaneRef.get();
            if (oldPane != null) {
                oldPane.headerTextProperty().removeListener(this.headerTextListener);
                oldPane.getButtonTypes().removeListener(this.buttonsListener);

            }

            DialogPane newPane = this.getDialogPane();
            if (newPane != null) {
                newPane.headerTextProperty().addListener(this.headerTextListener);
                newPane.getButtonTypes().addListener(this.buttonsListener);
                newPane.getStylesheets().add("dialogue.css");
                newPane.getStyleClass().add("myDialog");
            }

            this.dialogPaneRef = new WeakReference(newPane);
        }

        public static enum AlertType {
            NONE,
            INFORMATION,
            WARNING,
            CONFIRMATION,
            ERROR;

            private AlertType() {
            }
        }
    }
    