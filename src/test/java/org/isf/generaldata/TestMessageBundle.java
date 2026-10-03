/*
 * Open Hospital (www.open-hospital.org)
 * Copyright © 2006-2024 Informatici Senza Frontiere (info@informaticisenzafrontiere.org)
 *
 * Open Hospital is a free and open source software for healthcare data management.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * https://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package org.isf.generaldata;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ResourceBundle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class TestMessageBundle {

	@AfterEach
	void forgetTheBundles() {
		setBundles(null, false);
	}

	/** As in openhospital-api: the bundle is not initialized. */
	private static void setBundles(ResourceBundle resourceBundle, boolean serverBundleLoaded) {
		ReflectionTestUtils.setField(MessageBundle.class, "resourceBundle", resourceBundle);
		ReflectionTestUtils.setField(MessageBundle.class, "serverResourceBundle", null);
		ReflectionTestUtils.setField(MessageBundle.class, "serverResourceBundleLoaded", serverBundleLoaded);
	}

	@Test
	void testGetServerMessage_translatedWhereMessagesKeepTheirKeys() {
		GeneralData.LANGUAGE = "en";
		setBundles(null, false);

		assertThat(MessageBundle.getMessage("angal.common.reminder.txt")).isEqualTo("angal.common.reminder.txt");
		assertThat(MessageBundle.getServerMessage("angal.common.reminder.txt")).isEqualTo("Reminder");
		assertThat(MessageBundle.getServerMessage("someKey")).isEqualTo("someKey");
	}

	@Test
	void testGetServerMessage_theMessageWhereTheBundleIsInitialized() {
		GeneralData.LANGUAGE = "en";
		MessageBundle.initialize();

		assertThat(MessageBundle.getServerMessage("angal.common.reminder.txt")).isEqualTo(MessageBundle.getMessage("angal.common.reminder.txt"));
	}

	@Test
	void testGetMessageBundle() {
		GeneralData.LANGUAGE = "en";

		MessageBundle.getBundle();

		assertThat(MessageBundle.getMessage("someKey")).isEqualTo("someKey");
		assertThat(MessageBundle.getMnemonic("someKey")).isEqualTo(83);
	}
}
