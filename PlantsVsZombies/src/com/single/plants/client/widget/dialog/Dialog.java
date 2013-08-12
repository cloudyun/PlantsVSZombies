package com.single.plants.client.widget.dialog;

import java.util.ArrayList;
import java.util.List;

import com.framework.client.Framework;
import com.framework.client.event.BaseEvent;
import com.framework.client.widget.Component;
import com.framework.client.widget.ComponentHelper;
import com.framework.client.widget.ModalPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;
import com.single.plants.client.event.PVZEvent;
import com.single.plants.client.model.PVZResources;
import com.single.plants.client.widget.tool.Button;

public class Dialog extends Component implements PVZResources {
	private static class BlankComponent extends Component {
		public BlankComponent() {
			setElement(DOM.createDiv());
		}
	}

	protected Component content = new BlankComponent();

	private static DialogUiBinder uiBinder = GWT.create(DialogUiBinder.class);

	interface DialogUiBinder extends UiBinder<Element, Dialog> {
	}

	@UiField
	DivElement bodies;
	protected Dialog() {
		super("dialog");
		setElement(uiBinder.createAndBindUi(this));
	}

	public Dialog(Component content, Button... buttons) {
		this();
		this.content = content;
		for (Button b : buttons) {
			addButton(b);
		}
		setSize("400px", "400px");
	}

	protected List<Button> buttons = new ArrayList<Button>();

	protected boolean addButton(Button button) {
		button.removeFromParent();
		if (button.isAttached()) {
			button.el().removeFromParent();
		}
		buttons.add(button);

		return true;
	}

	@Override
	protected void onAttach() {
		super.onAttach();
		content.getElement().getStyle().setPosition(Position.ABSOLUTE);
		content.el().setZIndex(10);
		content.el().setLeft(40);
		content.el().setTop(80);
		content.el().setWidth(el().getWidth() - 80);
		getElement().appendChild(content.getElement());

		bodies.getStyle().setHeight(el().getHeight() - 44, Unit.PX);
		ComponentHelper.doAttach(content);

		if (buttons.size() == 4) {
			addStyleDependentName("big");
		}
		if (buttons.size() == 1) {
			Button b = buttons.get(0);
			b.el().setWidth((int) (el().getWidth() * 0.8));
			b.el().setLeft(el().getWidth() / 10);
			b.el().setTop(el().getHeight() - 80);
		} else if (buttons.size() == 2) {
			Button b1 = buttons.get(0);
			b1.el().setWidth((int) (el().getWidth() * 0.4));
			b1.el().setTop(el().getHeight() - 85);
			b1.el().setLeft(el().getWidth() / 20);
			Button b2 = buttons.get(1);
			b2.el().setWidth((int) (el().getWidth() * 0.4));
			b2.el().setTop(el().getHeight() - 85);
			b2.el().setLeft(el().getWidth() / 2);
		} else if (buttons.size() == 4) {
			Button b1 = buttons.get(0);
			b1.el().setWidth((int) (el().getWidth() * 0.4));
			b1.el().setTop(el().getHeight() - 120);
			b1.el().setLeft(el().getWidth() / 20);
			Button b2 = buttons.get(1);
			b2.el().setWidth((int) (el().getWidth() * 0.4));
			b2.el().setTop(el().getHeight() - 120);
			b2.el().setLeft(el().getWidth() / 2);
			Button b3 = buttons.get(2);
			b3.el().setWidth((int) (el().getWidth() * 0.4));
			b3.el().setTop(el().getHeight() - 75);
			b3.el().setLeft(el().getWidth() / 20);
			Button b4 = buttons.get(3);
			b4.el().setWidth((int) (el().getWidth() * 0.4));
			b4.el().setTop(el().getHeight() - 75);
			b4.el().setLeft(el().getWidth() / 2);
		}
	}

	@Override
	protected void doAttachChildren() {
		super.doAttachChildren();
		if (isAttached()) {
			for (Button b : buttons) {
				DOM.appendChild(getElement(), b.getElement());
				b.setParent(this);
				ComponentHelper.doAttach(b);
			}
		}
	}

	@Override
	protected void doDetachChildren() {
		super.doDetachChildren();
		for (Button b : buttons) {
			ComponentHelper.doDetach(b);
		}
	}

	private ModalPanel modalPanel;

	@Override
	public void show() {
		if (!isAttached()) {
			RootPanel.get().add(this);
			el().setLeft((800 - el().getWidth()) / 2);
			el().setTop((600 - el().getHeight()) / 2);
			el().setZIndex(Framework.getTopZIndex() + 100);
		}

		modalPanel = ModalPanel.pop();
		modalPanel.show(this);
	}

	@Override
	public void hide() {

		RootPanel.get().remove(this);
		ModalPanel.push(modalPanel);
		modalPanel = null;

		fireEvent(PVZEvent.OnHide, new BaseEvent(this));
	}
}
