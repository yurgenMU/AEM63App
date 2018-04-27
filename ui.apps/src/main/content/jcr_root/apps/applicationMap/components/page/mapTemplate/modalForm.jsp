<%@include file="/apps/applicationMap/globalLib/global.jsp" %>

<div id="myModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 id="myModalLabel">
                    <%= i18n.get("parameters") %>
                </h3>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <%= i18n.get("name") %>
                    <input type="textfield" id="name" style="min-width: 100%" id="name" required></form>
                    <br>
                    <%= i18n.get("description") %>
                    <textarea class="form-control" style="min-height: 50%" id="eventText" required></textarea>
                </div>

                <div class="modal-footer">
                    <button class="btn" data-dismiss="modal" aria-hidden="true"><%= i18n.get("close") %>
                    </button>
                    <button class="btn btn-primary" data-dismiss="modal" type="submit"
                            id="saveButton"><%= i18n.get("save") %>
                    </button>
                    <button class="btn btn-danger" data-dismiss="modal" type="submit"
                            id="deleteButton"><%= i18n.get("delete") %>
                    </button>
                </div>

            </div>

        </div>
    </div>
</div>
